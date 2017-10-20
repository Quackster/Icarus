package org.alexdev.icarus.messages.outgoing.room.floorplan;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FloorPlanFloorMapComposer extends MessageComposer {

    private List<Item> floorItems;

    public FloorPlanFloorMapComposer(List<Item> list) {
        this.floorItems = list;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FloorPlanFloorMapMessageComposer);
        response.writeInt(this.floorItems.size());

        for (Item item : this.floorItems) {
            response.writeInt(item.getPosition().getX());
            response.writeInt(item.getPosition().getY());
        }

    }

    @Override
    public short getHeader() {
        return Outgoing.FloorPlanFloorMapMessageComposer;
    }
}
