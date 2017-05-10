package org.alexdev.icarus.messages.outgoing.item;

import java.util.List;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class InventoryLoadMessageComposer implements OutgoingMessageComposer {

	private List<Item> wallItems;
	private List<Item> floorItems;

	public InventoryLoadMessageComposer(Player player) {
		this(player.getInventory().getWallItems(), player.getInventory().getFloorItems());
	}

	public InventoryLoadMessageComposer(List<Item> wallItems, List<Item> floorItems) {
		this.wallItems = wallItems;
		this.floorItems = floorItems;
	}

	@Override
	public void write(AbstractResponse response) {

		response.init(Outgoing.InventoryLoadMessageComposer);
		response.appendInt32(1);
		response.appendInt32(0);
		response.appendInt32(this.wallItems.size() + this.floorItems.size());

		for (Item item : this.wallItems) {

			response.appendInt32(item.getGameId());
			response.appendString(item.getData().getType().toUpperCase());
			response.appendInt32(item.getGameId());
			response.appendInt32(item.getData().getSpriteId());

			if (item.getData().getItemName().contains("landscape"))
				response.appendInt32(4);
			else if (item.getData().getItemName().contains("wallpaper"))
				response.appendInt32(2);
			else if (item.getData().getItemName().contains("a2")) 
				response.appendInt32(3);
			else
				response.appendInt32(1);

			response.appendInt32(0);
			response.appendString(item.getExtraData());
			response.appendBoolean(item.getData().allowRecycle());
			response.appendBoolean(item.getData().allowTrade());
			response.appendBoolean(item.getData().allowInventoryStack());
			response.appendBoolean(item.getData().allowMarketplaceSell());
			response.appendInt32(-1);
			response.appendBoolean(false);
			response.appendInt32(-1);
		}

		for (Item item : floorItems) {

			response.appendInt32(item.getGameId());
			response.appendString(item.getData().getType().toUpperCase());
			response.appendInt32(item.getGameId());
			response.appendInt32(item.getData().getSpriteId());

			if (item.getData().getInteractionType() == InteractionType.GROUPITEM || item.getData().getInteractionType() == InteractionType.GLD_GATE) {
				response.appendInt32(17); 
			} else if (item.getData().getInteractionType() == InteractionType.MUSICDISK) {
				response.appendInt32(8);
			} else {
				response.appendInt32(1);
			}

			response.appendInt32(0);
			response.appendString(item.getExtraData());
			response.appendBoolean(item.getData().allowRecycle());
			response.appendBoolean(item.getData().allowTrade());
			response.appendBoolean(item.getData().allowInventoryStack());
			response.appendBoolean(item.getData().allowMarketplaceSell());
			response.appendInt32(-1);
			response.appendBoolean(false); 
			response.appendInt32(-1);
			response.appendString("");
			response.appendInt32(0);
		}

	}

}
