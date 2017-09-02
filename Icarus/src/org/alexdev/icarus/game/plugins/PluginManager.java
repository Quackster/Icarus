package org.alexdev.icarus.game.plugins;

import org.alexdev.icarus.log.Log;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class PluginManager {

	public static void load() {
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
		}
		
		for (int i = 0; i < table.len().toint(); i++) {
			LuaValue value = table.get(i + 1);
			loadPlugin(value.toString());
		}
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
		
		LuaValue eventsValue = globals.get("events");

		if (!eventsValue.istable()) {
			return;

		}
		
		LuaTable details = (LuaTable) detailsValue;
		LuaTable events = (LuaTable) eventsValue;
		
		Plugin plugin = new Plugin(
				details.get("name").toString(), 
				details.get("author").toString());
		
		LuaValue pluginEnable = globals.get("onEnable");
		pluginEnable.invoke(CoerceJavaToLua.coerce(plugin));
		
		for (int i = 0; i < events.len().toint(); i++) {
			LuaValue value = events.get(i + 1);
		}
		
	}

	private static void registerGlobalVariables(Globals globals) {
		globals.set("log", CoerceJavaToLua.coerce(new Log()));
		
	}
}
