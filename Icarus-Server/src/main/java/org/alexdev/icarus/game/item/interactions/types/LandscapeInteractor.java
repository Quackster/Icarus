package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.room.user.RoomUser;

public class LandscapeInteractor extends Interaction {

    @Override
    public ExtraData createExtraData(Item item) {
        return new StringExtraData(ExtraDataPerspective.OUTSIDE_DECORATION, item.getExtraData());
    }
}
