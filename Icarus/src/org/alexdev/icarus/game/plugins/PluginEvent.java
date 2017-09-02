package org.alexdev.icarus.game.plugins;

public enum PluginEvent {

	PLAYER_LOGIN_EVENT("onPlayerLogin", "PlayerLoginEvent");
	
	private String functionName;
	private String eventName;
	
	private PluginEvent(String functionName, String eventName) {
		this.functionName = functionName;
		this.eventName = eventName;
	}

	public String getFunctionName() {
		return functionName;
	}
	
	public String getEventName() {
		return eventName;
	}


}
