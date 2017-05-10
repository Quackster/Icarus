package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.FloorItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.ChatOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.WallItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.server.messages.AbstractReader;

public class HeightmapMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {

		Room room = player.getRoomUser().getRoom();

		if (room == null) {
			return;
		}

		if (room.getEntities().contains(player)) {
			return;
		}

		RoomModel roomModel = room.getData().getModel();
		
		room.firstEntry(); // this method will load all pets AND items if this is the first user to join the room

		player.send(new HeightMapMessageComposer(room, room.getData().getModel().getMapSizeX(), room.getData().getModel().getMapSizeY()));
		player.send(new FloorMapMessageComposer(room));

		player.getRoomUser().setLoadingRoom(false);
		player.getRoomUser().setInRoom(true);
		
		RoomUser user = player.getRoomUser();

		user.setVirtualId(room.getVirtualId());
		user.setPosition(room.getData().getModel().getDoorPosition());
		user.getPosition().setZ(roomModel.getHeight(user.getPosition().getX(), user.getPosition().getY()));
		
		user.setRotation(roomModel.getDoorRot(), false);

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
			player.getRoomUser().getStatuses().put("flatctrl", "1");
		}		

		player.send(new RoomDataMessageComposer(room, player, true, true));

		player.send(new ChatOptionsMessageComposer(room));
		player.send(new WallOptionsMessageComposer(room.getData().isHideWall(), room.getData().getWallThickness(), room.getData().getFloorThickness()));

		player.send(new FloorItemsMessageComposer(room.getItemManager().getFloorItems()));
		player.send(new WallItemsMessageComposer(room.getItemManager().getWallItems()));
		
		player.getMessenger().sendStatus(false);

	}
}