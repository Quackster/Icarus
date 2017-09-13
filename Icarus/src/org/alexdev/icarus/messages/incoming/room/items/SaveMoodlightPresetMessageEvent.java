package org.alexdev.icarus.messages.incoming.room.items;

import java.util.List;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.moodlight.MoodlightData;
import org.alexdev.icarus.game.item.moodlight.MoodlightManager;
import org.alexdev.icarus.game.item.moodlight.MoodlightPreset;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveMoodlightPresetMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int presetId = reader.readInt();
        boolean backgroundOnly = reader.readInt() == 2;
        String colour = reader.readString();
        int colorIntensity = reader.readInt();
        
        if (!MoodlightManager.isValidColor(colour)) {
            return;
        }
        
        if (!MoodlightManager.isValidIntensity(colorIntensity)) {
            return;
        }
        
        List<Item> items = player.getRoom().getItemManager().getItems(InteractionType.DIMMER);
        
        Item moodlight = null;
        
        for (Item item : items) {
            moodlight = item;
            break;
        }

        if (moodlight == null) {
            return;
        }
        
        MoodlightData data = MoodlightManager.getMoodlightData(moodlight.getId());
        
        MoodlightPreset preset = data.getPresets().get(presetId - 1);
        preset.setBackgroundOnly(backgroundOnly);
        preset.setColorCode(colour);
        preset.setColorIntensity(colorIntensity);
        
        data.setCurrentPreset(presetId);
        data.save();
        
        moodlight.setExtraData(data.generateExtraData());
        moodlight.updateStatus();
        moodlight.save();
    }

}
