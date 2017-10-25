package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.json.moodlight.MoodlightData;
import org.alexdev.icarus.game.item.json.moodlight.MoodlightPreset;
import org.alexdev.icarus.game.room.user.RoomUser;

public class DimmerInteractor extends Interaction {

    @Override
    public ExtraData createExtraData(Item item) {
        
        MoodlightData data = null;
        MoodlightPreset preset = null;

        if (item.getExtraData().length() > 0) {
            data = ExtraDataManager.getJsonData(item, MoodlightData.class);
            
            if (data.getPresets().size() == 0) {
                data.getPresets().add(new MoodlightPreset());
                data.getPresets().add(new MoodlightPreset());
                data.getPresets().add(new MoodlightPreset());
                ExtraDataManager.saveExtraData(item, data);
            }
            
            preset = data.getPresets().get(data.getCurrentPreset() - 1);    
            
        } else {
            data = new MoodlightData();
            preset = new MoodlightPreset();
        }

        StringBuilder builder = new StringBuilder();
        builder.append(data.isEnabled() ? 2 : 1);
        builder.append(",");
        builder.append(data.getCurrentPreset());
        builder.append(",");
        builder.append(preset.isBackgroundOnly() ? 2 : 1);
        builder.append(",");
        builder.append(preset.getColorCode());
        builder.append(",");
        builder.append(preset.getColorIntensity());
        
        return new StringExtraData(ExtraDataPerspective.FURNI, builder.toString());
    }
}
