package org.alexdev.icarus.game.furniture;

import java.util.List;
import java.util.Optional;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.dao.mysql.ItemDao;

public class FurnitureManager {

	private static List<Furniture> furniture;

	public static void load() {
		furniture = ItemDao.getFurniture();
	}

	public static Furniture getFurnitureById(int id) {
		
		Optional<Furniture> furni = furniture.stream().filter(item -> item.getId() == id).findFirst();

		if (furni.isPresent()) {
			return furni.get();
		} else {
			return null;
		}
	}

	public static List<Furniture> getFurniture() {
		return furniture;
	}
}
