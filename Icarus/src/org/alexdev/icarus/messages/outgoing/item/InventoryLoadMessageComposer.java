package org.alexdev.icarus.messages.outgoing.item;

import java.util.List;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

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
	public void write(Response response) {

		response.init(Outgoing.InventoryMessageComposer);
		response.writeInt(1);
		response.writeInt(0);
		response.writeInt(this.wallItems.size() + this.floorItems.size());

		for (Item item : this.wallItems) {

			response.writeInt(item.getGameId());
			response.writeString(item.getData().getType().toUpperCase());
			response.writeInt(item.getGameId());
			response.writeInt(item.getData().getSpriteId());

			if (item.getData().getItemName().contains("landscape"))
				response.writeInt(4);
			else if (item.getData().getItemName().contains("wallpaper"))
				response.writeInt(2);
			else if (item.getData().getItemName().contains("a2")) 
				response.writeInt(3);
			else
				response.writeInt(1);

			response.writeInt(0);
			response.writeString(item.getExtraData());
			response.writeBool(item.getData().allowRecycle());
			response.writeBool(item.getData().allowTrade());
			response.writeBool(item.getData().allowInventoryStack());
			response.writeBool(item.getData().allowMarketplaceSell());
			response.writeInt(-1);
			response.writeBool(false);
			response.writeInt(-1);
		}

		for (Item item : floorItems) {

			response.writeInt(item.getGameId());
			response.writeString(item.getData().getType().toUpperCase());
			response.writeInt(item.getGameId());
			response.writeInt(item.getData().getSpriteId());

			if (item.getData().getInteractionType() == InteractionType.GROUPITEM || item.getData().getInteractionType() == InteractionType.GLD_GATE) {
				response.writeInt(17); 
			} else if (item.getData().getInteractionType() == InteractionType.MUSICDISK) {
				response.writeInt(8);
			} else {
				response.writeInt(1);
			}

			response.writeInt(0);
			response.writeString(item.getExtraData());
			response.writeBool(item.getData().allowRecycle());
			response.writeBool(item.getData().allowTrade());
			response.writeBool(item.getData().allowInventoryStack());
			response.writeBool(item.getData().allowMarketplaceSell());
			response.writeInt(-1);
			response.writeBool(false); 
			response.writeInt(-1);
			response.writeString("");
			response.writeInt(0);
		}

	}

}
