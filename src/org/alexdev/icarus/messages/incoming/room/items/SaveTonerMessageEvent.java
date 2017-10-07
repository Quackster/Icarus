package org.alexdev.icarus.messages.incoming.room.items;

import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.gson.Gson;

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
        
        Map<String, Integer> settings =  new HashMap<String, Integer>();
        settings.put("hue", hue);
        settings.put("saturation", saturation);
        settings.put("brightness", brightness);
        settings.put("enabled", 1);
        
        Gson gson = new Gson();
      
        item.setExtraData(gson.toJson(settings));
        item.updateStatus();
        item.save();
    }
}
