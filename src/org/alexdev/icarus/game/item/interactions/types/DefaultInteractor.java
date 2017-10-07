package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.util.Util;

public class DefaultInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {

        if (!roomUser.getRoom().hasRights(roomUser.getEntity().getEntityId()) && !roomUser.getEntity().getDetails().hasPermission("room_all_rights")) {
            return;
        }

        int modes = item.getDefinition().getInteractionModes();

        if (modes > 0) {

            int currentMode = Util.isNumber(item.getExtraData()) ? Integer.valueOf(item.getExtraData()) : 0;
            int newMode = currentMode + 1;

            if (newMode > modes) {
                newMode = 0;
            }

            item.setExtraData(String.valueOf(newMode));
            item.updateStatus();
            item.save();

            if (item.getDefinition().getVariableHeight().length > 0) {
                item.getRoom().getMapping().regenerateCollisionMaps();
            }
        }
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
            
            double sitHeight = item.getDefinition().getHeight();
            String height = roomUser.getEntity().getType() == EntityType.PET ? String.valueOf(sitHeight / 2) : String.valueOf(sitHeight);
             
            roomUser.setStatus(EntityStatus.SIT, height);
            roomUser.getPosition().setRotation(item.getPosition().getRotation());
        }
    }
}
