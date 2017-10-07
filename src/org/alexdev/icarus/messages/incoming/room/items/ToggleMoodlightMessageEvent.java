package org.alexdev.icarus.messages.incoming.room.items;

import java.lang.reflect.Type;
import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.item.moodlight.MoodlightData;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        
        Type type = new TypeToken<MoodlightData>(){}.getType();
        
        MoodlightData data = new Gson().fromJson(moodlight.getExtraData(), type);
        data.setEnabled(!data.isEnabled());
        
        moodlight.setExtraData(new Gson().toJson(data));
        moodlight.updateStatus();
        moodlight.save();
    }

}
