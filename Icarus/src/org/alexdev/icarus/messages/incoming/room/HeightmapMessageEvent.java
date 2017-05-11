package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.ChatOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.FloorItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.WallItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class HeightmapMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {

		Room room = player.getRoomUser().getRoom();

		if (room == null) {
			return;
		}
		
		if (room.getEntities().contains(player)) {
			return;
		}
		
		room.firstEntry();

		player.send(new HeightMapMessageComposer(room, room.getModel().getMapSizeX(), room.getModel().getMapSizeY()));
		player.send(new FloorMapMessageComposer(room));

		room.send(new UserDisplayMessageComposer(player));
		room.send(new UserStatusMessageComposer(player));

		if (!room.getEntities().contains(player)) {
			room.getEntities().add(player);
		}

		player.send(new UserDisplayMessageComposer(room.getEntities()));
		player.send(new UserStatusMessageComposer(room.getEntities()));

		for (Player players : room.getUsers()) {
			if (players.getRoomUser().isDancing()) {
				player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
			}
		}

		if (room.hasRights(player, false)) {
			player.getRoomUser().setStatus("flatctrl", " 1", true, -1);
		}		

		player.send(new RoomDataMessageComposer(room, player, true, true));

		player.send(new ChatOptionsMessageComposer(room));
		player.send(new WallOptionsMessageComposer(room.getData().isHideWall(), room.getData().getWallThickness(), room.getData().getFloorThickness()));

		player.send(new FloorItemsMessageComposer(room.getFloorItems()));
		player.send(new WallItemsMessageComposer(room.getWallItems()));
		
		player.getMessenger().sendStatus(false);

	}
}