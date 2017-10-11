package org.alexdev.icarus.game.plugins;

import org.luaj.vm2.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Plugin {

    private String pluginName;
    private String pluginAuthor;
    private boolean shutdown;
    
    private Globals globals;
    private Logger log;
    
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
        this.log = LoggerFactory.getLogger(this.pluginName);
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
        	log.error("Error occurred with registering plugin event:", e); 
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

	public Logger getLogger() {
		return log;
	}
}
