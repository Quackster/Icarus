package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.item.RemoveInventoryItemComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PlaceItemMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {

		String input = request.readString();

		String[] data = input.split(" ");
		int id = Integer.parseInt(data[0].replace("-", ""));

		Item item = player.getInventory().getItem(id);
		Room room = player.getRoomUser().getRoom();

		if (item == null) {
			return;
		}

		if (room == null) {
			return;
		}

		if (item.getType() == ItemType.WALL) {

            String[] posD = input.split(":")[1].split(" ");
            
            char side;
            
            if (posD[2].equals("l"))
                side = 'l';
            else
                side = 'r';

            String[] widD = posD[0].substring(2).split(",");
            int widthX = Integer.parseInt(widD[0]);
            int widthY = Integer.parseInt(widD[1]);

            String[] lenD = posD[1].substring(2).split(",");
            int lengthX = Integer.parseInt(lenD[0]);
            int lengthY = Integer.parseInt(lenD[1]);
            
            item.setWidthX(widthX);
            item.setWidthY(widthY);
            item.setSide(side);
            item.setLengthX(lengthX);
            item.setLengthY(lengthY);
			
		}

		if (item.getType() == ItemType.FLOOR) {

			int x = Integer.parseInt(data[1]);
			int y = Integer.parseInt(data[2]);
			int rotation = Integer.parseInt(data[3]);
			double height = player.getRoomUser().getRoom().getModel().getHeight(x, y);

			item.getPosition().setX(x);
			item.getPosition().setY(y);
			item.getPosition().setZ(height);
			item.setRotation(rotation);
		
		}
        
        room.getMapping().addItem(item);
		
		player.send(new RemoveInventoryItemComposer(item.getGameId()));
		
		player.getInventory().getItems().remove(item);
		player.getInventory().forceUpdate(false);
	}

}
