package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.util.Util;

public class AdjustableHeightInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {

        if (!roomUser.getRoom().hasRights(roomUser.getEntity().getEntityId()) && !roomUser.getEntity().getDetails().hasPermission("room_all_rights")) {
            return;
        }

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

        item.getRoom().getMapping().regenerateCollisionMaps();
        item.updateEntities();

    }

    @Override
    public boolean onStopWalking(Item item, RoomUser roomUser) { return false; }

}
