package org.alexdev.icarus.game.furniture.interactions.types;

import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.room.user.RoomUser;

public class GateInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        
        // Call default interactor, that's all the gate needs
        Interaction interaction = InteractionType.DEFAULT.getHandler();
        interaction.onUseItem(item, roomUser);
    }

    @Override
    public boolean onStopWalking(Item item, RoomUser roomUser) { return false; }
}
