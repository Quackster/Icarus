package org.alexdev.icarus.messages.incoming.room.items;

import java.util.List;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PlaceItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();
        
        if (room == null) {
            return;
        }

        if (!room.hasRights(player, false)) {
            return;
        }
        
        String input = reader.readString();

        String[] data = input.split(" ");
        int id = Integer.parseInt(data[0].replace("-", ""));
        
        Item item = player.getInventory().getItem(id);

        if (item == null) {
            return;
        }

        if (item.getDefinition().getInteractionType() == InteractionType.DIMMER) {
        	
    		List<Item> items = player.getRoom().getItems(InteractionType.DIMMER);
    		
    		if (items.size() > 0) {
    			return; // TODO: Alert no more dimmers
    		}
        }

        if (item.getType() == ItemType.WALL) {
            String[] pos = input.split(":")[1].split(" ");
            item.parseWallPosition(pos[2] + "," + pos[0].substring(2) + " " + pos[1].substring(2));
        }

        if (item.getType() == ItemType.FLOOR) {

            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);
            int rotation = Integer.parseInt(data[3]);
            double height = player.getRoomUser().getRoom().getModel().getHeight(x, y);

            item.getPosition().setX(x);
            item.getPosition().setY(y);
            item.getPosition().setZ(height);
            item.getPosition().setRotation(rotation);
        
        }
        
        room.getMapping().addItem(item);

        player.getInventory().remove(item);
        player.getInventory().update();
    }

}
