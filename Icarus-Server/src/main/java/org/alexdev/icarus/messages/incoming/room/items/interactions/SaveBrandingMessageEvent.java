package org.alexdev.icarus.messages.incoming.room.items.interactions;

import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.json.AdsBackgroundData;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveBrandingMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        int brandingId = reader.readInt();

        Room room = player.getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasOwnership(player.getEntityId()) && !player.hasPermission("room_all_rights")) {
            return;
        }

        Item item = room.getItemManager().getItem(brandingId);

        if (item == null) {
            return;
        }

        int length = reader.readInt();
        String brandingData = "state" + (char) 9 + "0";

        for (int i = 1; i <= length; i++) {
            brandingData = brandingData + (char) 9 + reader.readString();
        }

        String[] adsData = item.getExtraData().split(Character.toString((char) 9));
        Map<String, String> objects = new HashMap<>();

        for (int i = 0; i < adsData.length; i++) {
            objects.put(adsData[i], adsData[i + 1]);
            i++;
        }
        
        AdsBackgroundData data = new AdsBackgroundData();
        data.setState(Integer.valueOf(objects.get("state")));
        data.setOffsetX(Integer.valueOf(objects.get("offsetX")));
        data.setOffsetY(Integer.valueOf(objects.get("offsetY")));
        data.setOffsetZ(Integer.valueOf(objects.get("offsetZ")));
        data.setImageUrl(objects.get("imageUrl"));
        
        ExtraDataManager.saveExtraData(item, data);
        item.updateStatus();
        item.saveExtraData();
    }

}
