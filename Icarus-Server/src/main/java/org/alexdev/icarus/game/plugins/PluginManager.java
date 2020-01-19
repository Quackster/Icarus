package org.alexdev.icarus.game.plugins;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.util.Util;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PluginManager {

	private List<Plugin> plugins;
	private Map<PluginEvent, List<Plugin>> registeredPluginEvents;

	private final static Logger log = LoggerFactory.getLogger(PluginManager.class);
	private static PluginManager instance;

	public PluginManager() {
		this.plugins = new ArrayList<>();
		this.registeredPluginEvents = new ConcurrentHashMap<>();

		for (PluginEvent event : PluginEvent.values()) {
			this.registeredPluginEvents.put(event, new ArrayList<>());
		}

		getPluginFiles();
	}

	/**
	 * Gets the plugin files.
	 *
	 * @return the plugin files
	 */
	private void getPluginFiles() {
		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile("plugins" + File.separator + "plugin_registry.lua");
		chunk.call();
		
		log.info("Loading plugins");

		LuaValue tableValue = globals.get("plugins");

		if (!tableValue.istable()) {
			return;

		}

		LuaTable table = (LuaTable) tableValue;

		for (int i = 0; i < table.len().toint(); i++) {
			LuaValue value = table.get(i + 1);

			try {
				loadPlugin(value.toString());
				
			} catch (PluginException e) {
				log.error("Could not load plugin: ", e);
			}
		}

		log.info("Loaded " + this.plugins.size() + " plugin(s)!");
	}

	/**
	 * Load plugin.
	 *
	 * @param path the path
	 */
	private void loadPlugin(String path) throws PluginException {
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
	public boolean callEvent(PluginEvent event, LuaValue[] values) {
		if (!this.registeredPluginEvents.containsKey(event)) {
			return false;
		}

		for (Plugin plugin : this.registeredPluginEvents.get(event)) {

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
	public void disposePlugins() {
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
	private void registerGlobalVariables(LuaValue plugin, Globals globals) {
		globals.set("plugin", plugin);
		globals.set("catalogueManager", CoerceJavaToLua.coerce(new CatalogueManager()));
		globals.set("playerManager", CoerceJavaToLua.coerce(new PlayerManager()));
		globals.set("roomManager", CoerceJavaToLua.coerce(new RoomManager()));
		globals.set("util", CoerceJavaToLua.coerce(new Util()));
		globals.set("scheduler", CoerceJavaToLua.coerce(new PluginScheduler()));
	}

	/**
	 * Gets the plugins.
	 *
	 * @return the plugins
	 */
	public List<Plugin> getPlugins() {
		return this.plugins;
	}

	/**
	 * Gets the registered plugin events.
	 *
	 * @return the registered plugin events
	 */
	public Map<PluginEvent, List<Plugin>> getRegisteredPluginEvents() {
		return this.registeredPluginEvents;
	}

	/**
	 * Gets the instance
	 *
	 * @return the instance
	 */
	public static PluginManager getInstance() {

		if (instance == null) {
			instance = new PluginManager();
		}

		return instance;
	}

	public void reload() {
		// Might break shit so I'm not sure if I should add a reload here...
	}
}
