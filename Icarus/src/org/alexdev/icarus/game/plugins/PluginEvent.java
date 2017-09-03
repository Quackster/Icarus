package org.alexdev.icarus.game.plugins;

public enum PluginEvent {

	// Player events
	PLAYER_LOGIN_EVENT("onPlayerLoginEvent"),
	PLAYER_DISCONNECT_EVENT("onPlayerDisconnectEvent"),
	
	// Console messenger events
	MESSENGER_TALK_EVENT("onMessengerTalkEvent"),
	
	// Room events
	ROOM_REQUEST_ENTER_EVENT("onRoomRequestEvent"),
	ROOM_ENTER_EVENT("onRoomEnterEvent"),
	ROOM_LEAVE_EVENT("onRoomLeaveEvent"),
	
	// Item events
	PLACE_FLOOR_ITEM_EVENT("onPlaceFloorItemEvent"),
	PLACE_WALL_ITEM_EVENT("onPlaceFloorItemEvent"),
	FLOOR_ITEM_INTERACT_EVENT("onInteractFloorItemEvent"),
	WALL_ITEM_INTERACT_EVENT("onInteractWallItemEvent");
	
	private String functionName;
	
	private PluginEvent(String functionName) {
		this.functionName = functionName;
	}
	
	public String getFunctionName() {
		return functionName;
	}


}