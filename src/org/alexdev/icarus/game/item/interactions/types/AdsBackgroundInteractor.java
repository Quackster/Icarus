package org.alexdev.icarus.game.item.interactions.types;

import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.KeyValueExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.json.adsbackground.AdsBackgroundData;
import org.alexdev.icarus.game.room.user.RoomUser;

public class AdsBackgroundInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) { }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

    @Override
    public ExtraData createExtraData(Item item) {

        AdsBackgroundData data = null;
        Map<String, String> objects = new HashMap<>();

        if (item.getExtraData().length() > 0) {
            data = ExtraDataManager.getJsonData(item, AdsBackgroundData.class);
            
        } else {
            data = new AdsBackgroundData();
        }

        objects.put("imageUrl", data.getImageUrl());
        objects.put("offsetX", String.valueOf(data.getOffsetX()));
        objects.put("offsetY", String.valueOf(data.getOffsetY()));
        objects.put("offsetZ", String.valueOf(data.getOffsetZ()));
        objects.put("state", String.valueOf(data.getState()));
        
        return new KeyValueExtraData(ExtraDataPerspective.FLOOR_DECORATION, objects);
    }
}
