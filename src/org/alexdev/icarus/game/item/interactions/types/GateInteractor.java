package org.alexdev.icarus.game.item.interactions.types;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.room.user.RoomUser;

public class GateInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) {
        Interaction interaction = InteractionType.DEFAULT.get();
        interaction.onUseItem(item, roomUser);
    }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) { }
    
    @Override
    public ExtraData createExtraData(Item item) {
        return new StringExtraData(ExtraDataPerspective.FURNI, item.getExtraData());
    }
}
