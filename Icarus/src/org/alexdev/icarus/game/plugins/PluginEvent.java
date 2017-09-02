package org.alexdev.icarus.game.plugins;

public enum PluginEvent {

	// Player events
	PLAYER_LOGIN_EVENT("onPlayerLoginEvent"),
	
	// Room events
	ROOM_ENTER_EVENT("onRoomEnterEvent");
	
	private String functionName;
	
	private PluginEvent(String functionName) {
		this.functionName = functionName;
	}
	
	public String getFunctionName() {
		return functionName;
	}


}
