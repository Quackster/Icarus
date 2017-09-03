package org.alexdev.icarus.game.plugins;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.log.Log;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;

public class Plugin {

	private String pluginName;
	private String pluginAuthor;
	private Globals globals;
	
	public Plugin(String pluginName, String pluginAuthor, Globals globals) {
		this.pluginName = pluginName;
		this.pluginAuthor = pluginAuthor;
		this.globals = globals;
	}
	
	public void runTaskLater(int seconds, LuaValue function) {
		GameScheduler.getScheduler().schedule(() -> {
			function.invoke();
		}, seconds, TimeUnit.SECONDS);
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
