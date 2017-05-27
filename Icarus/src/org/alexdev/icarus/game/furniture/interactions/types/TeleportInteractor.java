package org.alexdev.icarus.game.furniture.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.dao.mysql.ItemDao;
import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.RoomUser;

public class TeleportInteractor implements Interaction {

    public static final String TELEPORTER_CLOSE = "0";
    public static final String TELEPORTER_OPEN = "1";
    public static final String TELEPORTER_EFFECTS = "2";
    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        
        if (item.getPosition().isMatch(roomUser.getPosition())) {
            
            item.setExtraData(TELEPORTER_OPEN);
            item.updateStatus();
            
            roomUser.walkTo(item.getPosition().getSquareInFront().getX(), item.getPosition().getSquareInFront().getY());
            
            RoomManager.getScheduler().schedule(() -> {
                item.setExtraData(TELEPORTER_CLOSE);
                item.updateStatus();
            }, 1, TimeUnit.SECONDS);
            
        }
        
        Position front = item.getPosition().getSquareInFront();

        if (!front.isMatch(roomUser.getPosition())) {
            roomUser.walkTo(front.getX(), front.getY());
            return;
        }

        int newRotation = item.getPosition().getRotation() - 4;

        if (roomUser.getPosition().getRotation() != newRotation) {
            roomUser.getPosition().setRotation(newRotation);
            roomUser.setNeedUpdate(true);
        }
        
        item.setExtraData(TELEPORTER_OPEN);
        item.updateStatus();
        
        roomUser.walkTo(item.getPosition().getX(), item.getPosition().getY());
 
        Item targetTeleporter = roomUser.getRoom().getItem(item.getTeleporterId());//ItemDao.getItem(item.getTeleporterId());
 
        // Do teleporter flashing effects for the teleporter they hopped into
        RoomManager.getScheduler().schedule(() -> {
            item.setExtraData(TELEPORTER_EFFECTS);
            item.updateStatus();
            
        }, 1, TimeUnit.SECONDS);
        
        // Stop previous teleporter from flashing and do the effects for the new teleporter
        RoomManager.getScheduler().schedule(() -> {
            item.setExtraData(TELEPORTER_CLOSE);
            item.updateStatus();
            
            roomUser.warpTo(targetTeleporter.getPosition().getX(), targetTeleporter.getPosition().getY(), targetTeleporter.getPosition().getRotation());
            
            targetTeleporter.setExtraData(TELEPORTER_EFFECTS);
            targetTeleporter.updateStatus();
            
        }, 2, TimeUnit.SECONDS);
        
        // Open up new teleporter and walk out of it
        RoomManager.getScheduler().schedule(() -> {
            targetTeleporter.setExtraData(TELEPORTER_OPEN);
            targetTeleporter.updateStatus();
            
            roomUser.walkTo(targetTeleporter.getPosition().getSquareInFront().getX(), targetTeleporter.getPosition().getSquareInFront().getY());
            
        }, 3, TimeUnit.SECONDS);
        
        // Close the teleporter
        RoomManager.getScheduler().schedule(() -> {
            targetTeleporter.setExtraData(TELEPORTER_CLOSE);
            targetTeleporter.updateStatus();
            
        }, 4, TimeUnit.SECONDS);
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

}
