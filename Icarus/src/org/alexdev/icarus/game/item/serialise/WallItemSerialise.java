package org.alexdev.icarus.game.item.serialise;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemSerialise;
import org.alexdev.icarus.server.api.messages.Response;

public class WallItemSerialise implements ItemSerialise {

    private Item item;
    
    public WallItemSerialise(Item item) {
        this.item = item;
    }

    @Override
    public void serialiseInventory(Response response) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void serialiseItem(Response response) {
        response.writeString(this.item.getId() + "");
        response.writeInt(this.item.getDefinition().getSpriteId());
        response.writeString(this.item.getWallPosition());
        response.writeString(this.item.getExtraData());
        response.writeInt(-1); // secondsToExpiration
        response.writeInt(this.item.getDefinition().getInteractionType() != InteractionType.DEFAULT ? 1 : 0);
        response.writeInt(this.item.getUserId());
    }
}
