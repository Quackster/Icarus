package org.alexdev.icarus.game.furniture;

import java.util.List;
import java.util.Optional;

import org.alexdev.icarus.dao.mysql.ItemDao;

public class FurnitureManager {

	private static List<ItemDefinition> furniture;

	public static void load() {
		furniture = ItemDao.getFurniture();
	}

	public static ItemDefinition getFurnitureById(int id) {
		
		Optional<ItemDefinition> furni = furniture.stream().filter(item -> item.getId() == id).findFirst();

		if (furni.isPresent()) {
			return furni.get();
		} else {
			return null;
		}
	}

	public static List<ItemDefinition> getFurniture() {
		return furniture;
	}
}
