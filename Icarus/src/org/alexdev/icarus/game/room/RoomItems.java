package org.alexdev.icarus.game.room;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.ItemDao;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;

import com.google.common.collect.Lists;

public class RoomItems {

	private List<Item> items;
	private Room room;

	public RoomItems(Room room) {
		this.room = room;
	}

	public void load() {

		this.dispose();
		this.items = ItemDao.getRoomItems(this.room.getData().getId());
	}


	public List<Item> getItems(InteractionType interactionType) {
		try {
			return items.stream().filter(item -> item.getData().getInteractionType() == interactionType).collect(Collectors.toList());
		} catch (Exception e) {
			return Lists.newArrayList();
		}
	}

	public void dispose() {

		if (this.items != null) {
			this.items.clear();
			this.items = null;
		}
	}

	public Item[] getFloorItems() {
		List<Item> floorItems = items.stream().filter(item -> item.getType() == ItemType.FLOOR).collect(Collectors.toList());
		return floorItems.toArray(new Item[floorItems.size()]);
	}

	public Item[] getWallItems() {
		List<Item> wallItems = items.stream().filter(item -> item.getType() == ItemType.WALL).collect(Collectors.toList());
		return wallItems.toArray(new Item[wallItems.size()]);
	}
	
	public List<Item> getItems() {
		return this.items;
	}

}
