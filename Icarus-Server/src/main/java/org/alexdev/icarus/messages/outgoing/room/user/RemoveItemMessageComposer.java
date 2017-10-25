package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RemoveItemMessageComposer extends MessageComposer {

    private Item item;

    public RemoveItemMessageComposer(Item item) {
        this.item = item;
    }

    @Override
    public void compose(Response response) {
        response.writeString(item.getId());
        response.writeBool(false);
        response.writeInt(item.getOwnerId());

        if (this.item.getDefinition().getType() == ItemType.FLOOR) {
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {

        if (this.item.getDefinition().getType() == ItemType.FLOOR) {
            return Outgoing.RemoveItemMessageComposer;
        }

        if (this.item.getDefinition().getType() == ItemType.WALL) {
            return Outgoing.RemoveWallItemMessageComposer;
        }

        return -1;
    }
}