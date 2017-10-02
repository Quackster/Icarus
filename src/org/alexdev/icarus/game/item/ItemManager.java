package org.alexdev.icarus.game.item;

import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.dao.mysql.item.ItemDao;

import com.google.common.collect.Maps;

public class ItemManager {

    private static Map<Integer, ItemDefinition> furnitureIds;
    private static Map<Integer, ItemDefinition> furnitureSpriteIds;

    /**
     * Load all item definitions.
     */
    public static void load() {
        furnitureIds = ItemDao.getFurniture();
        furnitureSpriteIds = Maps.newHashMap();

        for (Entry<Integer, ItemDefinition> set : furnitureIds.entrySet()) {
            furnitureSpriteIds.put(set.getValue().getSpriteId(), set.getValue());
        }
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

    /**
     * Gets the furniture by sprite id.
     *
     * @param id the id
     * @return the furniture by sprite id
     */
    public static ItemDefinition getFurnitureBySpriteId(int id) {

        if (furnitureIds.containsKey(id)) {
            return furnitureIds.get(id);
        }

        return null;
    }
}
