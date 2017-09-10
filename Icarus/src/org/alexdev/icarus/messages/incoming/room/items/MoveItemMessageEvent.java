package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MoveItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        if (!player.inRoom()) {
            return;
        }

        Room room = player.getRoom();

        if (!room.hasRights(player, false)) {
            return;
        }

        int itemId = reader.readInt();

        Item item = room.getItem(itemId);

        if (item == null) {
            return;
        }

        boolean rotation_only = false;

        if (item.getType() == ItemType.FLOOR) {

            int x = reader.readInt();
            int y = reader.readInt();
            int rotation = reader.readInt();

            if (item.getPosition().getX() == x && item.getPosition().getY() == y) {
                rotation_only = true;
            }

            item.getPosition().setX(x);
            item.getPosition().setY(y);
            item.getPosition().setRotation(rotation);
        } 
        
        if (item.getType() == ItemType.WALL) {
            String input = reader.readString();
            String[] pos = input.split(":")[1].split(" ");
            item.parseWallPosition(pos[2] + "," + pos[0].substring(2) + " " + pos[1].substring(2));

        }
        
        room.getMapping().updateItemPosition(item, rotation_only);
    }
}
