package org.alexdev.icarus.game.furniture.interactions;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.RoomUser;

public interface Interaction {
    public void onUseItem(Item item, RoomUser roomUser);
    public void onStopWalking(Item item, RoomUser roomUser);
}
