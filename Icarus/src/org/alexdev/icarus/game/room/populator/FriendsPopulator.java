package org.alexdev.icarus.game.room.populator;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;

import com.google.common.collect.Lists;

public class FriendsPopulator extends IRoomPopulator {

	@Override
	public List<Room> generateListing(boolean limit, Player player) {

		return Lists.newArrayList();
	}

}
