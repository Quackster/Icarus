package org.alexdev.icarus.factories;

import org.alexdev.icarus.game.room.Room;

public class RoomFactory {

	public static Room newRoom() {
		Room room = new Room();
		return room;
	}

}
