package org.alexdev.icarus.game.plugins;

import org.alexdev.icarus.log.Log;
import org.luaj.vm2.Globals;

public class Plugin {

    private String pluginName;
    private String pluginAuthor;
    private Globals globals;
    private boolean shutdown;
    
    /**
     * Instantiates a new plugin.
     *
     * @param pluginName the plugin name
     * @param pluginAuthor the plugin author
     * @param globals the globals
     */
    public Plugin(String pluginName, String pluginAuthor, Globals globals) {
        this.pluginName = pluginName;
        this.pluginAuthor = pluginAuthor;
        this.globals = globals;
    }
    
    /**
     * Register event.
     *
     * @param eventName the event name
     */
    public void registerEvent(String eventName) {
        try {
            PluginEvent event = PluginEvent.valueOf(eventName);
            PluginManager.getRegisteredPluginEvents().get(event).add(this);
        } catch (Exception e) {
            Log.exception(e);
        }
    }

    /**
     * Run task later.
     *
     * @param seconds the seconds
     * @param functionObject the function object
     * @param parameterObject the parameter object
     */
    public static void runTaskLater(int seconds, Object functionObject, Object parameterObject) {
        PluginScheduler.runTaskLater(seconds, functionObject, parameterObject);
    }
    
    /**
     * Run task asynchronously.
     *
     * @param functionObject the function object
     * @param parameterObject the parameter object
     */
    public static void runTaskAsynchronously(Object functionObject, Object parameterObject) {
        PluginScheduler.runTaskAsynchronously(functionObject, parameterObject);
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return pluginName;
    }
    
    /**
     * Gets the author.
     *
     * @return the author
     */
    public String getAuthor() {
        return pluginAuthor;
    }

    /**
     * Gets the globals.
     *
     * @return the globals
     */
    public Globals getGlobals() {
        return globals;
    }

    /**
     * Checks if is closed.
     *
     * @return true, if is closed
     */
    public boolean isClosed() {
        return shutdown;
    }

    /**
     * Sets the closed.
     *
     * @param shutdown the new closed
     */
    public void setClosed(boolean shutdown) {
        this.shutdown = shutdown;
    }
}
