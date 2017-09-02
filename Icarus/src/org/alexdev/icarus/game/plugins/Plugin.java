package org.alexdev.icarus.game.plugins;

import org.luaj.vm2.Globals;

public class Plugin {

	private String pluginName;
	private String pluginAuthor;
	private Globals globals;
	
	public Plugin(String pluginName, String pluginAuthor, Globals globals) {
		this.pluginName = pluginName;
		this.pluginAuthor = pluginAuthor;
		this.globals = globals;
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
}
