package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class PlaceItemMessageComposer extends OutgoingMessageComposer {

	private Item item;

	public PlaceItemMessageComposer(Item item) {
		this.item = item;
	}

	@Override
	public void write() {
		
		if (this.item.getType() == ItemType.FLOOR) {
			response.init(Outgoing.PlaceFloorItemMessageComposer); 
		} else {
			response.init(Outgoing.PlaceWallItemMessageComposer);
		}
		
        this.item.serialise(response);
        response.writeString(this.item.getOwnerData().getUsername());
		
	}

}
