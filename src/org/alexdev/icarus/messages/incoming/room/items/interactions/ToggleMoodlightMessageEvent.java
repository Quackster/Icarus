package org.alexdev.icarus.messages.incoming.room.items.interactions;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.item.json.moodlight.MoodlightData;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ToggleMoodlightMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        List<Item> items = player.getRoom().getItemManager().getItems(InteractionType.DIMMER);
        
        Item moodlight = null;
        
        for (Item item : items) {
            moodlight = item;
            continue;
        }

        if (moodlight == null) {
            return;
        }
        
        MoodlightData data = ExtraDataManager.getJsonData(moodlight, MoodlightData.class);
        data.setEnabled(!data.isEnabled());
        
        ExtraDataManager.saveExtraData(moodlight, data);
        
        moodlight.updateStatus();
        moodlight.save();
    }

}
