package org.alexdev.icarus.messages.incoming.room.items.interactions;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.item.json.MoodlightData;
import org.alexdev.icarus.game.item.json.MoodlightPreset;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveMoodlightPresetMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        Room room = player.getRoom();

        if (!room.hasRights(player.getEntityId()) && !player.hasPermission("room_all_rights")) {
            return;
        }
        
        int presetId = reader.readInt();
        boolean backgroundOnly = reader.readInt() == 2;
        String colour = reader.readString();
        int colorIntensity = reader.readInt();
        
        if (!isValidColour(colour)) {
            return;
        }
        
        if (!isValidIntensity(colorIntensity)) {
            return;
        }
        
        List<Item> items = room.getItemManager().getItems(InteractionType.DIMMER);
        
        Item moodlight = null;
        
        for (Item item : items) {
            moodlight = item;
            break;
        }

        if (moodlight == null) {
            return;
        }
        
        MoodlightData data = ExtraDataManager.getJsonData(moodlight, MoodlightData.class);
        data.setCurrentPreset(presetId);
        
        MoodlightPreset preset = data.getPresets().get(presetId - 1);
        preset.setBackgroundOnly(backgroundOnly);
        preset.setColorCode(colour);
        preset.setColorIntensity(colorIntensity);
        
        ExtraDataManager.saveExtraData(moodlight, data);
        moodlight.updateStatus();
        moodlight.saveExtraData();
    }
    
    /**
     * Checks if is valid intensity.
     *
     * @param intensity the intensity
     * @return true, if is valid intensity
     */
    public static boolean isValidIntensity(int intensity) {
        
        if (intensity < 0 || intensity > 255) {
            return false;
        }

        return true;
    }
    
    /**
     * Checks if is valid colour.
     *
     * @param colorCode the color code
     * @return true, if is valid colour
     */
    public static boolean isValidColour(String colourCode) {
        
        switch (colourCode) {
            case "#000000":
            case "#0053F7":
            case "#EA4532":
            case "#82F349":
            case "#74F5F5":
            case "#E759DE":
            case "#F2F851":
                return true;
            default:
                return false;
        }
    }

}
