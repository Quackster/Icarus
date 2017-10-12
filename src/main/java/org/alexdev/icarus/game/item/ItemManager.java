package org.alexdev.icarus.game.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.dao.mysql.item.ItemDao;

public class ItemManager {

    private static Map<Integer, ItemDefinition> furnitureIds;
    /**
     * Load all item definitions.
     */
    public static void load() {
        furnitureIds = ItemDao.getFurniture();
    }

    /**
     * Gets the furniture by id.
     *
     * @param id the id
     * @return the furniture by id
     */
    public static ItemDefinition getFurnitureById(int id) {

        if (furnitureIds.containsKey(id)) {
            return furnitureIds.get(id);
        }

        return null;
    }
}
