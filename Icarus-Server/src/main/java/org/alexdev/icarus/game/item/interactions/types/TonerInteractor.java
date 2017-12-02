package org.alexdev.icarus.game.item.interactions.types;

import java.util.ArrayList;
import java.util.List;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataManager;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.IntArrayExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.json.TonerData;

public class TonerInteractor extends Interaction {

    @Override
    public ExtraData createExtraData(Item item) {
        List<Integer> objects = new ArrayList<>();

        if (item.getExtraData().length() > 0) {
            TonerData data = ExtraDataManager.getJsonData(item, TonerData.class);
            objects.add(data.isEnabled() ? 1 : 0);
            objects.add(data.getHue());
            objects.add(data.getSaturation());
            objects.add(data.getBrightness());
        }

        return new IntArrayExtraData(ExtraDataPerspective.FURNI, objects);
    }
}
