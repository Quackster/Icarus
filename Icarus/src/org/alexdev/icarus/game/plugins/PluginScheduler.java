package org.alexdev.icarus.game.plugins;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.GameScheduler;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class PluginScheduler {

	public static void runTaskLater(int seconds, Object functionObject) {

		runTaskLater(seconds, functionObject, new LuaTable());
	}

	public static void runTaskLater(int seconds, Object functionObject, Object parameterObject) {

		if (!(functionObject instanceof LuaFunction)) {
			return;
		}

		if (!(parameterObject instanceof LuaTable)) {
			return;
		}

		LuaFunction function = (LuaFunction) functionObject;
		LuaTable parameters = (LuaTable) parameterObject;

		LuaValue[] parameterValues = new LuaValue[parameters.len().toint()];

		for (int i = 0; i < parameters.len().toint(); i++) {
			LuaValue value = parameters.get(i + 1);
			parameterValues[i] = value;
		}

		GameScheduler.getScheduler().schedule(() -> {
			function.invoke(LuaValue.varargsOf(parameterValues));
		}, seconds, TimeUnit.SECONDS);
	}
}
