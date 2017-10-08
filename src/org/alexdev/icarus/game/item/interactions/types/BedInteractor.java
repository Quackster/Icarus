package org.alexdev.icarus.game.item.interactions.types;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.user.RoomUser;

public class BedInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) { }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) {

        roomUser.getPosition().setRotation(item.getPosition().getRotation());
        roomUser.removeStatus(EntityStatus.DANCE);
        roomUser.removeStatus(EntityStatus.LAY);

        double sitHeight = item.getDefinition().getHeight();
        String height = roomUser.getEntity().getType() == EntityType.PET ? String.valueOf(sitHeight / 2) : String.valueOf(sitHeight);

        roomUser.setStatus(EntityStatus.LAY, height);
    }


}
