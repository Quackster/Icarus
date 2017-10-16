package org.alexdev.icarus.game.item.interactions.types;

import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.KeyValueExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.json.mannequin.MannequinData;
import org.alexdev.icarus.game.room.user.RoomUser;

public class MannequinInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) { }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

    @Override
    public boolean allowStopWalkingUpdate(final Item item) {
        return false;
    }

    @Override
    public ExtraData createExtraData(Item item) {
        
        Map<String, String> objects = new HashMap<>();
        MannequinData data = null;
        
        if (item.getExtraData().length() > 0) {
            data = ExtraDataManager.getJsonData(item, MannequinData.class);
        } else {
            data = new MannequinData();
        }
        
        objects.put("GENDER", data.getGender());
        objects.put("FIGURE", data.getFigure());
        objects.put("OUTFIT_NAME", data.getOutfitName());
        
        return new KeyValueExtraData(ExtraDataPerspective.FURNI, objects);
    }
}
