package org.alexdev.icarus.game.plugins;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class PluginManager {

    private static List<Plugin> plugins;
    private static Map<PluginEvent, List<Plugin>> registeredPluginEvents;

    /**
     * Load.
     */
    public static void load() {

        plugins = Lists.newArrayList();
        registeredPluginEvents = Maps.newConcurrentMap();

        for (PluginEvent event : PluginEvent.values()) {
            registeredPluginEvents.put(event, Lists.newArrayList());
        }

        getPluginFiles();
    }

    /**
     * Gets the plugin files.
     *
     * @return the plugin files
     */
    private static void getPluginFiles() {

        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile("plugins" + File.separator + "plugin_registry.lua");
        chunk.call();

        LuaValue tableValue = globals.get("plugins");

        if (!tableValue.istable()) {
            return;

        }

        LuaTable table = (LuaTable) tableValue;

        if (table.len().toint() > 0) {
            Log.info();
        } else {
            return;
        }

        for (int i = 0; i < table.len().toint(); i++) {
            LuaValue value = table.get(i + 1);

            try {
                loadPlugin(value.toString());
            } catch (PluginException e) {
                Log.exception(e);
            }
        }

        Log.info("Loaded " + plugins.size() + " plugin(s)!");
    }

    /**
     * Load plugin.
     *
     * @param path the path
     */
    private static void loadPlugin(String path) throws PluginException {

        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile("plugins" + File.separator + path);
        chunk.call();

        LuaValue detailsValue = globals.get("plugin_details");

        if (!detailsValue.istable()) {
            return;
        }

        LuaTable details = (LuaTable) detailsValue;

        Plugin plugin = new Plugin(
                details.get("name").toString(), 
                details.get("author").toString(),
                globals);

        LuaValue luaPlugin = CoerceJavaToLua.coerce(plugin);
        LuaValue pluginEnable = globals.get("onEnable");
        LuaValue pluginEventRegister = globals.get("registerEvents");

        registerGlobalVariables(luaPlugin, globals);

        if (pluginEnable.isfunction()) {
            pluginEnable.invoke();
        }   else {
            throw new PluginException("The function 'onEnable' was not found, could not not start '" + plugin.getName() + "'");
        }

        if (pluginEventRegister.isfunction()) {
            pluginEventRegister.invoke();
        }

        plugins.add(plugin);
    }

    /**
     * Call event.
     *
     * @param event the event
     * @param values the values
     * @return true, if successful
     */
    public static boolean callEvent(PluginEvent event, LuaValue[] values) {

        if (!registeredPluginEvents.containsKey(event)) {
            return false;
        }

        for (Plugin plugin : registeredPluginEvents.get(event)) {

            LuaValue calledEvent = plugin.getGlobals().get(event.getFunctionName());
            Varargs variableArgs = calledEvent.invoke(LuaValue.varargsOf(values));

            boolean isCancelled = variableArgs.arg1().toboolean();

            if (isCancelled) {
                return true;
            }
        }

        return false;
    }

    /**
     * Dispose plugins.
     */
    public static void disposePlugins() {

        for (Plugin plugin : plugins) {
            plugin.setClosed(true);
        }

        plugins.clear();
    }

    /**
     * Register global variables.
     *
     * @param globals the globals
     */
    private static void registerGlobalVariables(LuaValue plugin, Globals globals) {

        // Plugin
        globals.set("plugin", plugin);

        // Managers
        globals.set("catalogueManager", CoerceJavaToLua.coerce(new CatalogueManager()));
        globals.set("playerManager", CoerceJavaToLua.coerce(new PlayerManager()));
        globals.set("roomManager", CoerceJavaToLua.coerce(new RoomManager()));

        // Utilities
        globals.set("log", CoerceJavaToLua.coerce(new Log()));
        globals.set("util", CoerceJavaToLua.coerce(new Util()));
        globals.set("scheduler", CoerceJavaToLua.coerce(new PluginScheduler()));
    }

    /**
     * Gets the plugins.
     *
     * @return the plugins
     */
    public static List<Plugin> getPlugins() {
        return plugins;
    }

    /**
     * Gets the registered plugin events.
     *
     * @return the registered plugin events
     */
    public static Map<PluginEvent, List<Plugin>> getRegisteredPluginEvents() {
        return registeredPluginEvents;
    }
}
