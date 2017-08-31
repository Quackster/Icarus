package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.incoming.room.RoomPromotionMessageComposer;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchaseRoomPromotionMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage reader) {
		
		reader.readInt();
		reader.readInt();
		
		int roomId = reader.readInt();
		
		Room eventRoom = null;
		
		for (Room room : player.getRooms()) {
			if (room.getData().getId() == roomId) {
				eventRoom = room;
				break;
			}
		}
		
		if (eventRoom == null) {
			return;
		}
		
		String promotionName = reader.readString();
		reader.readBoolean();
		String promotionDescription = reader.readString();
		
		eventRoom.createPromotion(promotionName, promotionDescription);
		eventRoom.send(new RoomPromotionMessageComposer(eventRoom));
		
		player.send(new PurchaseNotificationMessageComposer());

	}

}
