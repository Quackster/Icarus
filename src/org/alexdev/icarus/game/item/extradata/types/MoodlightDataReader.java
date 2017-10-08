package org.alexdev.icarus.game.item.extradata.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraDataReader;
import org.alexdev.icarus.game.item.moodlight.MoodlightData;

public class MoodlightDataReader extends ExtraDataReader<MoodlightData> {

    public MoodlightData getMoodlightData(Item item) {
        return super.getJsonData(item, MoodlightData.class);
    }
}
