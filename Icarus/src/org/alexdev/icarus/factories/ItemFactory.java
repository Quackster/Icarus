package org.alexdev.icarus.factories;

import org.alexdev.icarus.game.furniture.Furniture;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;

public class ItemFactory {

	public static Item newItem() {
		Item item = new Item();
		return item;
	}
	
	public static Item getItem(long databaseId, int userId, int itemId, int roomId, String x, String y, double z, int rotation, String extraData) {
		Item item = new Item();
		item.fill(databaseId, userId, itemId, roomId, x, y, z, rotation, extraData);
		return item;
	}
	
	public static Furniture getFurniture(int id, String publicName, String itemName, String type, int width, int length, double stackHeight,
			boolean canStack, boolean canSit, boolean isWalkable, int spriteId, boolean allowRecycle,
			boolean allowTrade, boolean allowMarketplaceSell, boolean allowGift, boolean allowInventoryStack,
			InteractionType interactionType, int interationModes, String[] vendingIds) {
		
		Furniture furniture = new Furniture();
		furniture.fill(id, publicName, itemName, type, width, length, stackHeight, canStack, canSit, isWalkable, spriteId, allowRecycle, allowTrade, allowMarketplaceSell, allowGift, allowInventoryStack, interactionType, interationModes, vendingIds);
		return furniture;
	}
}
