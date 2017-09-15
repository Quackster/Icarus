package org.alexdev.icarus.messages.incoming.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveBrandingMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int brandingId = reader.readInt();

        Room room = player.getRoom();

        if (room == null) {
            return;
        }
        
        if (!room.hasRights(player.getDetails().getId(), true)) {
            return;
        }
        
        int length = reader.readInt();
        String data = "state" + (char) 9 + "0";

        for (int i = 1; i <= length; i++) {
            data = data + (char) 9 + reader.readString();
        }

        Item item = room.getItemManager().getItem(brandingId);
        item.setExtraData(data);
        item.updateStatus();
        item.save();
    }

}
