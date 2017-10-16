package org.alexdev.icarus.game.item.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.game.util.RoomUtil;

public class TeleportInteractor extends Interaction {

    public static final String TELEPORTER_CLOSE = "0";
    public static final String TELEPORTER_OPEN = "1";
    public static final String TELEPORTER_EFFECTS = "2";
    
    @Override
    public void onUseItem(Item item, RoomUser roomUser) {

        // Check if the user is inside the teleporter, if so, walk out. Useful if the user is stuck inside.
        if (item.getPosition().equals(roomUser.getPosition())) {

            item.setExtraData(TELEPORTER_OPEN);
            item.updateStatus();

            roomUser.walkTo(item.getPosition().getSquareInFront().getX(), item.getPosition().getSquareInFront().getY());

            RoomManager.getScheduledPool().schedule(() -> {
                item.setExtraData(TELEPORTER_CLOSE);
                item.updateStatus();
            }, 1, TimeUnit.SECONDS);

            return;
        }

        // Checks if the connected teleporter is available and in use. If not, cancel all interaction.
        Position front = item.getPosition().getSquareInFront();

        if (!front.equals(roomUser.getPosition())) {
            Item targetTeleporter = roomUser.getRoom().getItemManager().getItem(item.getTeleporterId());

            if (targetTeleporter == null) {
                return;
            }

            if (targetTeleporter.getRoom() != null) {
                roomUser.walkTo(front.getX(), front.getY());
            }
            return;
        }

        item.setExtraData(TELEPORTER_OPEN);
        item.updateStatus();

        roomUser.walkTo(item.getPosition().getX(), item.getPosition().getY());
        roomUser.setWalkingAllowed(false);

        Item targetTeleporter = roomUser.getRoom().getItemManager().getItem(item.getTeleporterId());

       RoomManager.getScheduledPool().schedule(() -> {
            item.setExtraData(TELEPORTER_EFFECTS);
            item.updateStatus();

        }, 1, TimeUnit.SECONDS);

        RoomManager.getScheduledPool().schedule(() -> {
            item.setExtraData(TELEPORTER_CLOSE);
            item.updateStatus();

            targetTeleporter.setExtraData(TELEPORTER_EFFECTS);
            targetTeleporter.updateStatus();

        }, 2, TimeUnit.SECONDS);


        RoomManager.getScheduledPool().schedule(() -> {
            if (targetTeleporter.getRoomId() != item.getRoomId()) {            
                roomUser.setTeleporting(true);
                roomUser.setTeleportRoomId(targetTeleporter.getRoomId());

                RoomUtil.playerRoomEntry((Player)roomUser.getEntity(), targetTeleporter.getRoom(), 
                        targetTeleporter.getPosition().getX(), 
                        targetTeleporter.getPosition().getY(), 
                        targetTeleporter.getPosition().getRotation());
            } else {
                roomUser.warpTo(
                        targetTeleporter.getPosition().getX(), 
                        targetTeleporter.getPosition().getY(), 
                        targetTeleporter.getPosition().getRotation());
            }

            if (targetTeleporter.getRoomId() == item.getRoomId()) {
                targetTeleporter.setExtraData(TELEPORTER_OPEN);
                targetTeleporter.updateStatus();
            } else {
                roomUser.getRoom().getItemManager().getItem(item.getTeleporterId()).setExtraData(TELEPORTER_OPEN);
                roomUser.getRoom().getItemManager().getItem(item.getTeleporterId()).updateStatus();
            }

        }, 3, TimeUnit.SECONDS);

        RoomManager.getScheduledPool().schedule(() -> {
            roomUser.setWalkingAllowed(true);
            roomUser.walkTo(
                    targetTeleporter.getPosition().getSquareInFront().getX(), 
                    targetTeleporter.getPosition().getSquareInFront().getY());
            
        }, 3500, TimeUnit.MILLISECONDS);

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
}
