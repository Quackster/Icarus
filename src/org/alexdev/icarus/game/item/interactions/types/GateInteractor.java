package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.room.user.RoomUser;

public class GateInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        Interaction interaction = InteractionType.DEFAULT.getHandler();
        interaction.onUseItem(item, roomUser);
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }
}