package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.util.Util;

public class DiceInteractor  implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {

        //int modes = item.getDefinition().getInterationModes();
        int current_mode = Util.getRandom().nextInt(6 + 1);
        
        item.setExtraData(String.valueOf(current_mode));
        item.updateStatus();
        item.save();
        
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) {
        // TODO Auto-generated method stub

    }
}