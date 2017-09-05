package org.alexdev.icarus.game.plugins;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;

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
    
    public void runTaskLater(int seconds, Object functionObject) {
        runTaskLater(seconds, functionObject, new LuaTable());
    }

    public void runTaskLater(int seconds, Object functionObject, Object parameterObject) {
        
        if (this.shutdown) {
            return;
        }
        
        PluginScheduler.runTaskLater(seconds, functionObject, parameterObject);
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

    public boolean isShutdown() {
        return shutdown;
    }

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }
}
