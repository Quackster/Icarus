package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.room.user.RoomUser;

public class BedInteractor extends Interaction {

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) {
        roomUser.getPosition().setRotation(item.getPosition().getRotation());
        roomUser.removeStatus(EntityStatus.DANCE);
        roomUser.removeStatus(EntityStatus.LAY);

        double sitHeight = item.getDefinition().getHeight();
        roomUser.setStatus(EntityStatus.LAY, roomUser.getEntity().getType() == EntityType.PET ? String.valueOf(sitHeight / 2) : String.valueOf(sitHeight));
    }

    @Override
    public boolean allowStopWalkingUpdate(Item item) {
        return true;
    }
}
