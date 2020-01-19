package org.alexdev.icarus.game.item.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.dao.mysql.item.TeleporterDao;
import org.alexdev.icarus.game.item.Item;
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
    public void useItem(Item item, RoomUser roomUser) {
        // Check if the user is inside the teleporter, if so, walk out. Useful if the user is stuck inside.
        if (item.getPosition().equals(roomUser.getPosition())) {
            item.setExtraData(TELEPORTER_OPEN);
            item.updateStatus();

            roomUser.walkTo(item.getPosition().getSquareInFront().getX(), item.getPosition().getSquareInFront().getY());

            RoomManager.getInstance().getScheduleService().schedule(() -> {
                item.setExtraData(TELEPORTER_CLOSE);
                item.updateStatus();
            }, 1, TimeUnit.SECONDS);
            return;
        }

        int pairId = TeleporterDao.getPairId(item.getId());

        if (!(pairId > 0)) {
            return;
        }

        Item targetTeleporter = roomUser.getRoom().getItemManager().getItem(pairId);

        if (targetTeleporter == null) {
            return;
        }

        if (!(targetTeleporter.getRoom() != null && targetTeleporter.getRoomId() > 0)) {
            return;
        }

        Position front = item.getPosition().getSquareInFront();

        if (!front.equals(roomUser.getPosition())) {
            roomUser.walkTo(front.getX(), front.getY());
            return;
        }

        item.setExtraData(TELEPORTER_OPEN);
        item.updateStatus();

        roomUser.walkTo(item.getPosition().getX(), item.getPosition().getY());
        roomUser.setWalkingAllowed(false);

       RoomManager.getInstance().getScheduleService().schedule(() -> {
            item.setExtraData(TELEPORTER_EFFECTS);
            item.updateStatus();
        }, 1, TimeUnit.SECONDS);

        RoomManager.getInstance().getScheduleService().schedule(() -> {
            item.setExtraData(TELEPORTER_CLOSE);
            item.updateStatus();

            targetTeleporter.setExtraData(TELEPORTER_EFFECTS);
            targetTeleporter.updateStatus();

        }, 2, TimeUnit.SECONDS);


        RoomManager.getInstance().getScheduleService().schedule(() -> {
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
                roomUser.getRoom().getItemManager().getItem(pairId).setExtraData(TELEPORTER_OPEN);
                roomUser.getRoom().getItemManager().getItem(pairId).updateStatus();
            }
        }, 3, TimeUnit.SECONDS);

        RoomManager.getInstance().getScheduleService().schedule(() -> {
            roomUser.setWalkingAllowed(true);
            roomUser.walkTo(
                    targetTeleporter.getPosition().getSquareInFront().getX(), 
                    targetTeleporter.getPosition().getSquareInFront().getY());
        }, 3500, TimeUnit.MILLISECONDS);

        RoomManager.getInstance().getScheduleService().schedule(() -> {
            if (targetTeleporter.getRoomId() == item.getRoomId()) {
                targetTeleporter.setExtraData(TELEPORTER_CLOSE);
                targetTeleporter.updateStatus();
            } else {
                roomUser.getRoom().getItemManager().getItem(pairId).setExtraData(TELEPORTER_CLOSE);
                roomUser.getRoom().getItemManager().getItem(pairId).updateStatus();
            }
        }, 4, TimeUnit.SECONDS);
    }
}
