package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.room.user.RoomUser;

public class GateInteractor extends Interaction {

    @Override
    public void useItem(Item item, RoomUser roomUser) {

        String extraData = null;

        if (item.isGateOpen()) {
            extraData = "0";
        } else {
            extraData = "1";
        }

        item.setExtraData(extraData);
        item.updateStatus();
        item.saveExtraData();
    }
}
