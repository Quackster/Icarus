package org.alexdev.icarus.game.plugins;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.GameScheduler;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class Plugin {

    private String pluginName;
    private String pluginAuthor;
    private Globals globals;
    private boolean shutdown;
    
    public Plugin(String pluginName, String pluginAuthor, Globals globals) {
        this.pluginName = pluginName;
        this.pluginAuthor = pluginAuthor;
        this.globals = globals;
    }

    public static void runTaskLater(int seconds, Object functionObject) {
        PluginScheduler.runTaskLater(seconds, functionObject, new LuaTable());
    }

    public static void runTaskLater(int seconds, Object functionObject, Object parameterObject) {
        PluginScheduler.runTaskLater(seconds, functionObject, parameterObject);
    }
    
    public static void runTaskAsynchronously(Object functionObject) {
        PluginScheduler.runTaskAsynchronously(functionObject, new LuaTable());
    }
    
    public static void runTaskAsynchronously(Object functionObject, Object parameterObject) {
        PluginScheduler.runTaskAsynchronously(functionObject, parameterObject);
    }
    
    public String getName() {
        return pluginName;
    }
    
    public String getAuthor() {
        return pluginAuthor;
    }

    public Globals getGlobals() {
        return globals;
    }

    public boolean isClosed() {
        return shutdown;
    }

    public void setClosed(boolean shutdown) {
        this.shutdown = shutdown;
    }
}
