package org.alexdev.icarus.game.furniture.interactions.types;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.RoomUser;

public class VendingInteractor implements Interaction {

    @Override
    public void onUseItem(final Item item, final RoomUser roomUser) {

        // Can't get to the vending machine unless we're close
        Position front = item.getPosition().getSquareInFront();

        if (!front.isMatch(roomUser.getPosition())) {
            roomUser.walkTo(front.getX(), front.getY());
            return;
        }

        int newRotation = item.getPosition().getRotation() - 4;

        if (roomUser.getPosition().getRotation() != newRotation) {
            roomUser.getPosition().setRotation(newRotation);
            roomUser.setNeedUpdate(true);
            return; // Don't give the drink immediately after we turn, just like in the old Shockwave versions :^)
        }
        
        item.setExtraData("1");
        item.updateStatus();

        RoomManager.getScheduler().schedule(() -> {
            roomUser.carryItem(item.getDefinition().getVendingID());
        }, 1, TimeUnit.SECONDS);

        RoomManager.getScheduler().schedule(() -> {
            item.setExtraData("0");
            item.updateStatus();
        }, 2, TimeUnit.SECONDS);
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }
}
