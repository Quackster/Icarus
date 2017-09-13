package org.alexdev.icarus.game.furniture.interactions;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.user.RoomUser;

public interface Interaction {
    public void onUseItem(final Item item, final RoomUser roomUser);
    public void onStopWalking(final Item item, final RoomUser roomUser);
}
