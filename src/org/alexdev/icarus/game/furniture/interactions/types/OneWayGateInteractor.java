package org.alexdev.icarus.game.furniture.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.log.Log;

public class OneWayGateInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {

        Position behind = item.getPosition().getSquareInFront();

        if (!behind.equals(roomUser.getPosition())) {
            roomUser.walkTo(behind.getX(), behind.getY());
            return;
        }
        
        Position front = item.getPosition().getSquareBehind();
       
        if (!roomUser.getRoom().getMapping().isTileWalkable(front.getX(), front.getY(), roomUser.getEntity())) {
            return;
        }
                
        item.setExtraData("1");
        item.updateStatus();
        
        roomUser.walkTo(front.getX(), front.getY());
        
        RoomManager.getScheduledPool().schedule(() -> {
            item.setExtraData("0");
            item.updateStatus();
        }, 2, TimeUnit.SECONDS);
    }

    @Override
    public boolean onStopWalking(Item item, RoomUser roomUser) { return false; }

}
