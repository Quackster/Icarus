package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.RoomUser;

public class DefaultInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) {
        
        if (item.getDefinition().isCanSit()) {
            
            roomUser.setStatus("sit", " " + (roomUser.getPosition().getZ() + 1), true, -1);
            roomUser.getPosition().setRotation(item.getPosition().getRotation());
        }
    }

}
