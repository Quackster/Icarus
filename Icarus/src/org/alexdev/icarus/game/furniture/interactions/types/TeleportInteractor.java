package org.alexdev.icarus.game.furniture.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.game.util.RoomUtil;

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

            RoomManager.getScheduledPool().schedule(() -> {
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
        
        Item targetTeleporter = roomUser.getRoom().getItemManager().getItem(item.getTeleporterId());

        // Do teleporter flashing effects for the teleporter they hopped into
        RoomManager.getScheduledPool().schedule(() -> {
            item.setExtraData(TELEPORTER_EFFECTS);
            item.updateStatus();

        }, 1, TimeUnit.SECONDS);

        // Stop previous teleporter from flashing and do the effects for the new teleporter
        RoomManager.getScheduledPool().schedule(() -> {
            item.setExtraData(TELEPORTER_CLOSE);
            item.updateStatus();

            targetTeleporter.setExtraData(TELEPORTER_EFFECTS);
            targetTeleporter.updateStatus();

        }, 2, TimeUnit.SECONDS);


        // Open up new teleporter and walk out of it
        RoomManager.getScheduledPool().schedule(() -> {

            if (targetTeleporter.getRoomId() != item.getRoomId()) {                
                roomUser.setTeleporting(true);
                roomUser.setTeleportRoomId(targetTeleporter.getRoomId());
                
                RoomUtil.playerRoomEntry((Player)roomUser.getEntity(), targetTeleporter.getRoom(), 
                        targetTeleporter.getPosition().getX(), 
                        targetTeleporter.getPosition().getY(), 
                        targetTeleporter.getPosition().getRotation());
            } else {
                roomUser.warpTo(targetTeleporter.getPosition().getX(), targetTeleporter.getPosition().getY(), targetTeleporter.getPosition().getRotation());
            }

            if (targetTeleporter.getRoomId() == item.getRoomId()) {
                targetTeleporter.setExtraData(TELEPORTER_OPEN);
                targetTeleporter.updateStatus();
            } else {
                roomUser.getRoom().getItemManager().getItem(item.getTeleporterId()).setExtraData(TELEPORTER_OPEN);
                roomUser.getRoom().getItemManager().getItem(item.getTeleporterId()).updateStatus();
            }

        }, 3, TimeUnit.SECONDS);

        // Leave the teleporter
        RoomManager.getScheduledPool().schedule(() -> {
            roomUser.setWalkingAllowed(true);
            roomUser.walkTo(targetTeleporter.getPosition().getSquareInFront().getX(), targetTeleporter.getPosition().getSquareInFront().getY());
        }, 3500, TimeUnit.MILLISECONDS);

        // Close the teleporter
        RoomManager.getScheduledPool().schedule(() -> {

            if (targetTeleporter.getRoomId() == item.getRoomId()) {
                targetTeleporter.setExtraData(TELEPORTER_CLOSE);
                targetTeleporter.updateStatus();
            } else {
                roomUser.getRoom().getItemManager().getItem(item.getTeleporterId()).setExtraData(TELEPORTER_CLOSE);
                roomUser.getRoom().getItemManager().getItem(item.getTeleporterId()).updateStatus();
            }

        }, 4, TimeUnit.SECONDS);
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }

}
