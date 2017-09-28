package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveMannequinMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Room room = player.getRoom();
        
        if (room == null) {
            return;
        }
        
        if (!room.hasRights(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }
        
        Item item = room.getItemManager().getItem(reader.readInt());
        
        if (item == null) {
            return;
        }
        
        String figure = "";

        for (String str : player.getDetails().getFigure().split("\\.")) {
            
            if (str.contains("hr") || str.contains("hd") || str.contains("he") || str.contains("ea") || str.contains("ha"))
                continue;

            figure += str + ".";
        }
        
        if (item.getExtraData().contains(Character.toString((char)5))) {
            String[] flags = item.getExtraData().split(Character.toString((char)5));
            item.setExtraData("m" + (char)5 + figure + (char)5 + flags[2]);
        } else {
            item.setExtraData("m" + (char)5 + figure + (char)5 + "Default");
        }
        
        item.updateStatus();
        item.save();
    }

}
