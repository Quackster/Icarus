package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.util.Util;

public class DiceInteractor extends Interaction {

    @Override
    public void useItem(Item item, RoomUser roomUser) {
        //int modes = item.getDefinition().getInterationModes();
        int current_mode = Util.getRandom().nextInt(6 + 1);
        
        item.setExtraData(String.valueOf(current_mode));
        item.updateStatus();
        item.saveExtraData();
    }
}