package org.alexdev.icarus.game.furniture.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.dao.mysql.ItemDao;
import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.RoomUser;

public class TeleportInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        
        if (item.getPosition().isMatch(roomUser.getPosition())) {
            
            item.setExtraData("1");
            item.updateStatus();
            
            roomUser.walkTo(item.getPosition().getSquareInFront().getX(), item.getPosition().getSquareInFront().getY());
            
            RoomManager.getScheduler().schedule(() -> {
                item.setExtraData("0");
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
        
        item.setExtraData("1");
        item.updateStatus();
        
        roomUser.walkTo(item.getPosition().getX(), item.getPosition().getY());
 
        Item targetTeleporter = roomUser.getRoom().getItem(item.getTeleporterId());//ItemDao.getItem(item.getTeleporterId());
 
        RoomManager.getScheduler().schedule(() -> {
            item.setExtraData("2");
            item.updateStatus();

            roomUser.warpTo(targetTeleporter.getPosition().getX(), targetTeleporter.getPosition().getY(), targetTeleporter.getPosition().getRotation());
            
        }, 1, TimeUnit.SECONDS);
        
        RoomManager.getScheduler().schedule(() -> {
            item.setExtraData("0");
            item.updateStatus();
            
            targetTeleporter.setExtraData("2");
            targetTeleporter.updateStatus();
            
            roomUser.walkTo(targetTeleporter.getPosition().getSquareInFront().getX(), targetTeleporter.getPosition().getSquareInFront().getY());
            
        }, 2, TimeUnit.SECONDS);
        
        RoomManager.getScheduler().schedule(() -> {
            targetTeleporter.setExtraData("1");
            targetTeleporter.updateStatus();
            
            roomUser.walkTo(targetTeleporter.getPosition().getSquareInFront().getX(), targetTeleporter.getPosition().getSquareInFront().getY());
            
        }, 3, TimeUnit.SECONDS);
        
        RoomManager.getScheduler().schedule(() -> {
            targetTeleporter.setExtraData("0");
            targetTeleporter.updateStatus();
            
        }, 4, TimeUnit.SECONDS);
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

}
