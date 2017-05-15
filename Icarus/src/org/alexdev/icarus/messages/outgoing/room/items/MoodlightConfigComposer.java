package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.moodlight.MoodlightData;
import org.alexdev.icarus.game.item.moodlight.MoodlightPreset;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class MoodlightConfigComposer extends OutgoingMessageComposer {

    private MoodlightData data;

    public MoodlightConfigComposer(MoodlightData data) {
        this.data = data;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.MoodlightConfigMessageComposer);
        this.response.writeInt(this.data.getPresets().size());
        this.response.writeInt(this.data.getCurrentPreset());
        
        int id = 1;
        
        for (MoodlightPreset preset : this.data.getPresets()) {
            
            this.response.writeInt(id);
            
            if (preset.isBackgroundOnly()) {
                this.response.writeInt(2);
            } else {
                this.response.writeInt(1);
            }

            this.response.writeString(preset.getColorCode());
            this.response.writeInt(preset.getColorIntensity());
            id++;
        }
    }

}
