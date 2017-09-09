package org.alexdev.icarus.game.furniture.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.Player;
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
            //roomUser.getPosition().setRotation(newRotation);
            //roomUser.setNeedUpdate(true);
        }

        item.setExtraData(TELEPORTER_OPEN);
        item.updateStatus();

        roomUser.walkTo(item.getPosition().getX(), item.getPosition().getY());
        roomUser.setWalkingAllowed(false);
        
        Item targetTeleporter = roomUser.getRoom().getItem(item.getTeleporterID());

        // Do teleporter flashing effects for the teleporter they hopped into
        RoomManager.getScheduler().schedule(() -> {
            item.setExtraData(TELEPORTER_EFFECTS);
            item.updateStatus();

        }, 1, TimeUnit.SECONDS);

        // Stop previous teleporter from flashing and do the effects for the new teleporter
        RoomManager.getScheduler().schedule(() -> {
            item.setExtraData(TELEPORTER_CLOSE);
            item.updateStatus();

            targetTeleporter.setExtraData(TELEPORTER_EFFECTS);
            targetTeleporter.updateStatus();

        }, 2, TimeUnit.SECONDS);


        // Open up new teleporter and walk out of it
        RoomManager.getScheduler().schedule(() -> {

            if (targetTeleporter.getRoomID() != item.getRoomID()) {                
                roomUser.setTeleporting(true);
                roomUser.setTeleportRoomID(targetTeleporter.getRoomID());
    
                targetTeleporter.getRoom().loadRoom((Player) roomUser.getEntity(), "", targetTeleporter.getPosition().getX(), targetTeleporter.getPosition().getY(), targetTeleporter.getPosition().getRotation());
            } else {
                roomUser.warpTo(targetTeleporter.getPosition().getX(), targetTeleporter.getPosition().getY(), targetTeleporter.getPosition().getRotation());
            }

            if (targetTeleporter.getRoomID() == item.getRoomID()) {
                targetTeleporter.setExtraData(TELEPORTER_OPEN);
                targetTeleporter.updateStatus();
            } else {
                roomUser.getRoom().getItem(item.getTeleporterID()).setExtraData(TELEPORTER_OPEN);
                roomUser.getRoom().getItem(item.getTeleporterID()).updateStatus();
            }

        }, 3, TimeUnit.SECONDS);

        // Leave the teleporter
        RoomManager.getScheduler().schedule(() -> {
            roomUser.setWalkingAllowed(true);
            roomUser.walkTo(targetTeleporter.getPosition().getSquareInFront().getX(), targetTeleporter.getPosition().getSquareInFront().getY());
        }, 3500, TimeUnit.MILLISECONDS);

        // Close the teleporter
        RoomManager.getScheduler().schedule(() -> {

            if (targetTeleporter.getRoomID() == item.getRoomID()) {
                targetTeleporter.setExtraData(TELEPORTER_CLOSE);
                targetTeleporter.updateStatus();
            } else {
                roomUser.getRoom().getItem(item.getTeleporterID()).setExtraData(TELEPORTER_CLOSE);
                roomUser.getRoom().getItem(item.getTeleporterID()).updateStatus();
            }

        }, 4, TimeUnit.SECONDS);
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

}
