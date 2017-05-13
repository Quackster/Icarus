package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class InteractItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int itemId = reader.readInt();
        
        Item item = player.getRoom().getItem(itemId);
        
        if (item == null) {
            return;
        }

        if (item.getDefinition().getInteractionType().getHandler() != null) {
            item.getDefinition().getInteractionType().getHandler().onUseItem(item, player.getRoomUser());
        }
    }

}
