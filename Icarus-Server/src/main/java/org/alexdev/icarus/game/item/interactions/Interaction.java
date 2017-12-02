package org.alexdev.icarus.game.item.interactions;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataPerspective;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.game.room.user.RoomUser;

public abstract class Interaction {
    
    /**
     * Called when the item is interacted.
     *
     * @param item the item
     * @param roomUser the room user
     */
    public void onUseItem(final Item item, final RoomUser roomUser) { }
    
    /**
     * Called when the user stops walking on the item.
     *
     * @param item the item
     * @param roomUser the room user
     */
    public void onStopWalking(final Item item, final RoomUser roomUser) {}

    /**
     * If the entity needs to be updated after walking on this item
     */
    public boolean allowStopWalkingUpdate(final Item item) { return false; }

    /**
     * Get extra data instance.
     */
    public ExtraData createExtraData(final Item item) {
        return new StringExtraData(ExtraDataPerspective.FURNI, item.getExtraData());
    }
}
