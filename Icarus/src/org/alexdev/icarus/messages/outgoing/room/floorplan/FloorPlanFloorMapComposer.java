package org.alexdev.icarus.messages.outgoing.room.floorplan;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class FloorPlanFloorMapComposer extends OutgoingMessageComposer {

	private Item[] floorItems;

	public FloorPlanFloorMapComposer(Item[] floorItems) {
		this.floorItems = floorItems;
	}

	@Override
	public void write() {
		this.response.init(Outgoing.FloorPlanFloorMapMessageComposer);
		this.response.writeInt(this.floorItems.length);
		
		for (Item item : this.floorItems) {
			this.response.writeInt(item.getPosition().getX());
			this.response.writeInt(item.getPosition().getY());
		}

	}

}
