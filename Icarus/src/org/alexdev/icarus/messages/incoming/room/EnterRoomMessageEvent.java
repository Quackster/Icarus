package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class EnterRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {

		Room room = RoomDao.getRoom(request.readInt(), true);

		if (room == null) {
			return;
		} else {
		    room.leaveRoom(player, false);
		}

		String pass = request.readString();
		
		if (room.getEntities().contains(player)) {
			return;
		}
		
		boolean isOwner = room.hasRights(player, true);
		player.send(new RoomOwnerRightsComposer(room.getData().getId(), isOwner));

		if (room.getData().getUsersNow() >= room.getData().getUsersMax()) {

			if (!player.getDetails().hasFuse("user_enter_full_rooms") && player.getDetails().getId() != room.getData().getOwnerId()) {

				player.send(new RoomEnterErrorMessageComposer(1));
				player.send(new HotelViewMessageComposer());
				return;
			}
		}

		if (room.getData().getState().getStateCode() > 0 && !room.hasRights(player, false)) {
			if (room.getData().getState() == RoomState.DOORBELL) {

				if (room.getUsers().size() > 0) {
					player.send(new HotelViewMessageComposer());
					player.send(new GenericDoorbellMessageComposer(1));
					room.send(new GenericDoorbellMessageComposer(player.getDetails().getUsername()), true);
				} else {

					player.send(new GenericNoAnswerDoorbellMessageComposer());
					player.send(new HotelViewMessageComposer());
				}

				return;
			}

			if (room.getData().getState() == RoomState.PASSWORD) {
				if (!pass.equals(room.getData().getPassword())) {
					player.send(new GenericErrorMessageComposer(-100002));
					player.send(new HotelViewMessageComposer());
					return;
				}
			}
		}
		
		room.loadRoom(player);
	}

}
