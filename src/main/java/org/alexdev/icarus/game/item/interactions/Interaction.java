package org.alexdev.icarus.game.item.interactions;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.room.user.RoomUser;

public interface Interaction {
    
    /**
     * Called when the item is interacted.
     *
     * @param item the item
     * @param roomUser the room user
     */
    public void onUseItem(final Item item, final RoomUser roomUser);
    
    /**
     * Called when the user stops walking on the item.
     *
     * @param item the item
     * @param roomUser the room user
     */
    public void onStopWalking(final Item item, final RoomUser roomUser);
    
    /**
     * Get extra data instance.
     */
    public ExtraData createExtraData(final Item item);
}
