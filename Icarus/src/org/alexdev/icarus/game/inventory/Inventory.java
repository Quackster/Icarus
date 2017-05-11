package org.alexdev.icarus.game.inventory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.InventoryDao;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.item.InventoryLoadMessageComposer;
import org.alexdev.icarus.messages.outgoing.item.UpdateInventoryMessageComposer;

public class Inventory {

	private boolean initalised;
	private Player player;
	private List<Item> items;

	public Inventory(Player player) {
		this.initalised = false;
		this.player = player;
	}
	
	public void init() {
		if (!this.initalised) {
			this.items = InventoryDao.getInventoryItems(this.player.getDetails().getId());
			this.initalised = true;
		}
	}

	public Item getItem(int id) {
		
		Optional<Item> inventoryItem = this.items.stream().filter(item -> item.getGameId() == id).findFirst();
		
		if (inventoryItem.isPresent()) {
			return inventoryItem.get();
		} else {
			return null;
		}
	}
	
	public void forceUpdate(boolean sendInventoryPacket) {
		
		this.player.send(new UpdateInventoryMessageComposer());
		
		if (sendInventoryPacket) {
			this.player.send(new InventoryLoadMessageComposer(this.player));
		}
	}

	public void dispose() {

		if (this.items != null) {
			this.items.clear();
			this.items = null;
		}
	}

	public boolean isInitalised() {
		return initalised;
	}

	public void setInitalised(boolean initalised) {
		this.initalised = initalised;
	}

	public List<Item> getItems() {
		return items;
	}

	public List<Item> getFloorItems() {
		return items.stream().filter(item -> item != null && item.getType() == ItemType.FLOOR).collect(Collectors.toList());
	}
	
	public List<Item> getWallItems() {
		return items.stream().filter(item -> item != null && item.getType() == ItemType.WALL).collect(Collectors.toList());
	}
}
