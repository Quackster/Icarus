package org.alexdev.icarus.game.plugins;

public class Plugin {

	private String pluginName;
	private String pluginAuthor;
	
	public Plugin(String pluginName, String pluginAuthor) {
		this.pluginName = pluginName;
		this.pluginAuthor = pluginAuthor;
	}

	public String getName() {
		return pluginName;
	}
	
	public String getAuthor() {
		return pluginAuthor;
	}
}
