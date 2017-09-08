package org.alexdev.icarus.messages.outgoing.room.floorplan;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class FloorPlanFloorMapComposer extends MessageComposer {

    private List<Item> floorItems;

    public FloorPlanFloorMapComposer(List<Item> list) {
        this.floorItems = list;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FloorPlanFloorMapMessageComposer);
        this.response.writeInt(this.floorItems.size());
        
        for (Item item : this.floorItems) {
            this.response.writeInt(item.getPosition().getX());
            this.response.writeInt(item.getPosition().getY());
        }

    }
}
