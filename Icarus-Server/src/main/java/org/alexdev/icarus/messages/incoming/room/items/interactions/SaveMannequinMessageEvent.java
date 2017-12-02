package org.alexdev.icarus.messages.incoming.room.items.interactions;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.json.MannequinData;
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
        
        if (!room.hasRights(player.getEntityId()) && !player.hasPermission("room_all_rights")) {
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
        
        MannequinData data = null;
        
        if (item.getExtraData().length() > 0) {
            data = ExtraDataManager.getJsonData(item, MannequinData.class);
            data.setFigure(figure);
            data.setGender("m");
        } else {
            data = new MannequinData();
            data.setFigure(figure);
            data.setGender("m");
            data.setOutfitName("Default Mannequin");
        }
        
        ExtraDataManager.saveExtraData(item, data);
        item.updateStatus();
        item.saveExtraData();
    }

}
