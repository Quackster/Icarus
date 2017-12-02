package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.room.user.RoomUser;

public class RollerInteractor extends Interaction {

    @Override
    public void useItem(Item item, RoomUser roomUser) { }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

    @Override
    public boolean allowStopWalkingUpdate(final Item item) {
        return false;
    }

    @Override
    public ExtraData createExtraData(Item item) {
        return new StringExtraData(ExtraDataPerspective.FURNI, item.getExtraData());
    }
}
