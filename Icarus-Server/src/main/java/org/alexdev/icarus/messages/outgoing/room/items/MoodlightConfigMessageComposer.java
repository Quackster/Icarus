package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.json.MoodlightData;
import org.alexdev.icarus.game.item.json.MoodlightPreset;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MoodlightConfigMessageComposer extends MessageComposer {

    private MoodlightData data;

    public MoodlightConfigMessageComposer(MoodlightData data) {
        this.data = data;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.MoodlightConfigMessageComposer);
        response.writeInt(this.data.getPresets().size());
        response.writeInt(this.data.getCurrentPreset());

        int id = 1;

        for (MoodlightPreset preset : this.data.getPresets()) {

            response.writeInt(id);

            if (preset.isBackgroundOnly()) {
                response.writeInt(2);
            } else {
                response.writeInt(1);
            }

            response.writeString(preset.getColorCode());
            response.writeInt(preset.getColorIntensity());
            id++;
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.MoodlightConfigMessageComposer;
    }
}