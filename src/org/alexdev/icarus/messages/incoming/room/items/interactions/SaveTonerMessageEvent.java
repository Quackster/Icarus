package org.alexdev.icarus.messages.incoming.room.items.interactions;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.json.toner.TonerData;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveTonerMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Room room = player.getRoom();

        if (!room.hasRights(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }
        
        int id = reader.readInt();
        
        Item item = room.getItemManager().getItem(id);
        
        if (item == null) {
            return;
        }
        
        int hue = reader.readInt();
        int saturation = reader.readInt();
        int brightness = reader.readInt();
        
        if (hue > 255 || saturation > 255 || brightness > 255) {
            return;
        }
        
        TonerData backgroundData = new TonerData();
        backgroundData.setHue(hue);
        backgroundData.setSaturation(saturation);
        backgroundData.setBrightness(brightness);
        backgroundData.setEnabled(true);
        
        ExtraDataManager.saveExtraData(item, backgroundData);
      
        item.updateStatus();
        item.save();
    }
}
