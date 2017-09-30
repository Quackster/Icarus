package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

public class DefaultInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {

        if (item.getDefinition().requiresRights()) {
            if (!roomUser.getRoom().hasRights(roomUser.getEntity().getEntityId()) && !roomUser.getEntity().getDetails().hasPermission("room_all_rights")) {
                return;
            }
        }

        int modes = item.getDefinition().getInteractionModes();
        int currentMode = Util.isNumber(item.getExtraData()) ? Integer.valueOf(item.getExtraData()) : 0;
        int newMode = currentMode + 1;

        if (newMode >= modes) {
            currentMode = 0;
        } else {
            currentMode = newMode;
        }

        item.setExtraData(String.valueOf(currentMode));
        item.updateStatus();
        item.save();
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) {
        
        if (!item.getDefinition().allowSitOrLay()) {
            
            if (roomUser.containsStatus(EntityStatus.LAY)) {
                roomUser.removeStatus(EntityStatus.LAY);
            }

            if (roomUser.containsStatus(EntityStatus.SIT)) {
                roomUser.removeStatus(EntityStatus.SIT);
            }
        }
        
        if (item.getDefinition().allowSit()) {
            
            roomUser.setStatus(EntityStatus.SIT, roomUser.getEntity().getType() == EntityType.PET ? "0.5" : "1.0");
            roomUser.getPosition().setRotation(item.getPosition().getRotation());
        }
        
    }
}
