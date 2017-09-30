package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.user.RoomUser;

public class MannequinInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) { }

    @Override
    public boolean onStopWalking(Item item, RoomUser roomUser) { return false; }

}
