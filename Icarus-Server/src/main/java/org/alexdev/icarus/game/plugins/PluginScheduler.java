package org.alexdev.icarus.game.plugins;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.log.Log;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class PluginScheduler {

    /**
     * Command to run a Lua script on a delay with parameters defined by a {@link LuaTable}
     * 
     * @param seconds - the amount of seconds to tick before the task is run
     * @param functionObject - the {@link LuaValue} of the function
     * @param parameterObject - the {@link LuaTable} of parameters
     */
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

        try {
            GameScheduler.getInstance().getScheduler().schedule(() -> {
                function.invoke(LuaValue.varargsOf(parameterValues));
            }, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
        	Log.getErrorLogger().error("Error loading Lua method: ", e);
        }
    }
    

    /**
     * Command to run a Lua script on a delay with parameters defined by a {@link LuaTable} 
     * but it's called asynchronously without delay.
     *
     * @param functionObject - the {@link LuaValue} of the function
     * @param parameterObject - the {@link LuaTable} of parameters
     */
    public static void runTaskAsynchronously(Object functionObject, Object parameterObject) {

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

        try {
            GameScheduler.getInstance().getScheduler().execute(() -> {
                function.invoke(LuaValue.varargsOf(parameterValues));
            });
        } catch (Exception e) {
        	Log.getErrorLogger().error("Error loading Lua method: ", e);
        }
    }
}
