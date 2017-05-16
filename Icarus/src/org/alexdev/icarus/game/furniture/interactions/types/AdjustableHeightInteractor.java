package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

public class AdjustableHeightInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        
        int modes = item.getDefinition().getVariableHeight().length;
        int current_mode = Util.isNumber(item.getExtraData()) ? Integer.valueOf(item.getExtraData()) : 0;
        int new_mode = current_mode + 1;
        
        if (new_mode >= modes) {
            current_mode = 0;
        } else {
            current_mode = new_mode;
        }

        item.setExtraData(String.valueOf(current_mode));
        item.updateStatus();
        item.save();
        
        Log.println("Regenerate collision map: " + current_mode);
        item.getRoom().getMapping().regenerateCollisionMaps();
        
    }
    
    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

}
