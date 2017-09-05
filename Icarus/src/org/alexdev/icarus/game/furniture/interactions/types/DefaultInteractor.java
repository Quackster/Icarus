package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.util.Util;

public class DefaultInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        
        int modes = item.getDefinition().getInterationModes();
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
    }
    
    @Override
    public void onStopWalking(Item item, RoomUser roomUser) {
        
        if (item.getDefinition().allowSit()) {
            roomUser.setStatus(EntityStatus.SIT, roomUser.getEntity().getType() == EntityType.PET ? "0.5" : "1.0");
            roomUser.getPosition().setRotation(item.getPosition().getRotation());
        }
    }

}
