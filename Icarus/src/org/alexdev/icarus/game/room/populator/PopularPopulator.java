package org.alexdev.icarus.game.room.populator;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;

public class PopularPopulator extends IRoomPopulator {

	@Override
	public List<Room> generateListing(boolean limit, Player player) {

		List<Room> loadedRooms =  RoomManager.getLoadedRooms();
		List<Room> activeRooms = loadedRooms.stream().filter(r -> r.getData().getUsersNow() > 0).collect(Collectors.toList());
		
		activeRooms.sort((room1, room2)
		->room2.getData().getUsersNow()
		- room1.getData().getUsersNow());

		return activeRooms;
	}

}
