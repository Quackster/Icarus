package org.alexdev.icarus.messages.incoming.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.types.MoodlightDataReader;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.item.moodlight.MoodlightData;
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
        
        MoodlightDataReader dataReader = (MoodlightDataReader) InteractionType.DIMMER.getExtraDataReader();
        MoodlightData data = dataReader.getMoodlightData(moodlight);
        
        data.setEnabled(!data.isEnabled());
        dataReader.saveExtraData(moodlight, data);
        
        moodlight.updateStatus();
        moodlight.save();
    }

}
