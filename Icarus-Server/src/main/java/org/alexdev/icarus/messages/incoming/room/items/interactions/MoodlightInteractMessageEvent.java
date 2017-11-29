package org.alexdev.icarus.messages.incoming.room.items.interactions;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.item.json.moodlight.MoodlightData;
import org.alexdev.icarus.game.item.json.moodlight.MoodlightPreset;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.items.MoodlightConfigMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MoodlightInteractMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Room room = player.getRoom();

        if (!room.hasRights(player.getEntityId()) && !player.hasPermission("room_all_rights")) {
            return;
        }
        
        List<Item> items = room.getItemManager().getItems(InteractionType.DIMMER);
        
        Item moodlight = null;
        
        for (Item item : items) {
            moodlight = item;
            continue;
        }

        if (moodlight == null) {
            return;
        }
        
        MoodlightData data = null;
        
        if (moodlight.getExtraData().isEmpty()) {
            data = new MoodlightData();
            
            data.getPresets().add(new MoodlightPreset());
            data.getPresets().add(new MoodlightPreset());
            data.getPresets().add(new MoodlightPreset());
            
            ExtraDataManager.saveExtraData(moodlight, data);
            
        } else {
            data = ExtraDataManager.getJsonData(moodlight, MoodlightData.class);
        }

        moodlight.saveExtraData();
        player.send(new MoodlightConfigMessageComposer(data));
    }
}