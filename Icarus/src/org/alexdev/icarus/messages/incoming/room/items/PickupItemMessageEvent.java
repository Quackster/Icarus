package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PickupItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player, false)) {
            return;
        }

        // Junk
        reader.readInt();

        int itemId = reader.readInt();

        Item item = room.getItemManager().getItem(itemId);

        if (item == null) {
            return;
        }

        if (item.getType() == ItemType.FLOOR || item.getType() == ItemType.WALL) {

            room.getMapping().removeItem(item);
            
            player.getInventory().addItem(item);
            player.getInventory().updateItems();
        }
    }
}
