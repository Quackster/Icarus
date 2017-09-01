package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class EditRoomPromotionMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage reader) {
		
		int roomId = reader.readInt();
		String promotionName = reader.readString();	
		String promotionDescription = reader.readString();
		
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
		
		if (eventRoom.getPromotion() == null) {
			return;
		}
		
		eventRoom.getPromotion().setPromotionName(promotionName);
		eventRoom.getPromotion().setPromotionDescription(promotionDescription);
		eventRoom.send(new RoomPromotionMessageComposer(eventRoom));

	}

}
