package org.alexdev.icarus.messages.incoming.room.items;

import java.util.List;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.moodlight.MoodlightData;
import org.alexdev.icarus.game.item.moodlight.MoodlightManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ToggleMoodlightMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        List<Item> items = player.getRoom().getItems(InteractionType.DIMMER);
        
        Item moodlight = null;
        
        for (Item item : items) {
            moodlight = item;
            continue;
        }

        if (moodlight == null) {
            return;
        }
        
        MoodlightData data = MoodlightManager.getMoodlightData(moodlight.getID());
        data.setEnabled(!data.isEnabled());
        data.save();
        
        moodlight.setExtraData(data.generateExtraData());
        moodlight.updateStatus();
        moodlight.save();
    }

}
