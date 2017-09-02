package org.alexdev.icarus.game.plugins;

import java.util.List;
import java.util.Map;

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

	public static void load() {
		
		plugins = Lists.newArrayList();
		registeredPluginEvents = Maps.newConcurrentMap();
		
		for (PluginEvent event : PluginEvent.values()) {
			registeredPluginEvents.put(event, Lists.newArrayList());
		}
		
		getPluginFiles();
	}

	private static void getPluginFiles() {

		Globals globals = JsePlatform.standardGlobals();
		LuaValue chunk = globals.loadfile("plugin_registry.lua");
		chunk.call();

		LuaValue tableValue = globals.get("plugins");

		if (!tableValue.istable()) {
			return;

		}

		LuaTable table = (LuaTable) tableValue;

		if (table.len().toint() > 0) {
			Log.println();
		} else {
			return;
		}

		for (int i = 0; i < table.len().toint(); i++) {
			LuaValue value = table.get(i + 1);
			loadPlugin(value.toString());
		}
		
		Log.println("Loaded " + plugins.size() + " plugin(s)!");
	}

	private static void loadPlugin(String path) {

		Globals globals = JsePlatform.standardGlobals();
		registerGlobalVariables(globals);
		LuaValue chunk = globals.loadfile(path);
		chunk.call();

		LuaValue detailsValue = globals.get("plugin_details");

		if (!detailsValue.istable()) {
			return;

		}

		LuaValue eventsValue = globals.get("event_register");

		if (!eventsValue.istable()) {
			return;

		}

		LuaTable details = (LuaTable) detailsValue;
		LuaTable events = (LuaTable) eventsValue;

		Plugin plugin = new Plugin(
				details.get("name").toString(), 
				details.get("author").toString(),
				globals);
		
		plugins.add(plugin);

		LuaValue pluginEnable = globals.get("onEnable");
		pluginEnable.invoke(CoerceJavaToLua.coerce(plugin));
		
		for (int i = 0; i < events.len().toint(); i++) {
			PluginEvent event = PluginEvent.valueOf(events.get(i + 1).toString());
			registeredPluginEvents.get(event).add(plugin);
		}

	}
	
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

	private static void registerGlobalVariables(Globals globals) {
		globals.set("log", CoerceJavaToLua.coerce(new Log()));
		globals.set("util", CoerceJavaToLua.coerce(new Util()));

	}
}
