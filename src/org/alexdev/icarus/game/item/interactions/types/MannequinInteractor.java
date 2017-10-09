package org.alexdev.icarus.game.item.interactions.types;

import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.KeyValueExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.room.user.RoomUser;

public class MannequinInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) { }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }
    
    @Override
    public ExtraData createExtraData(Item item) {
        
        Map<String, String> objects = new HashMap<>();
        
        if (item.getExtraData().contains(Character.toString((char)5))) {
            String[] mannequinData = item.getExtraData().split(Character.toString((char)5));
            objects.put("GENDER", mannequinData[0]);
            objects.put("FIGURE", mannequinData[1]);
            objects.put("OUTFIT_NAME", mannequinData[2]);
            
        } else {
            objects.put("GENDER", "");
            objects.put("FIGURE", "");
            objects.put("OUTFIT_NAME", "");
        }
        
        return new KeyValueExtraData(ExtraDataPerspective.FURNI, objects);
    }
}
