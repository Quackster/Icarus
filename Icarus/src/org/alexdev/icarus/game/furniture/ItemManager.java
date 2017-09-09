package org.alexdev.icarus.game.furniture;

import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.dao.mysql.item.ItemDao;

import com.google.common.collect.Maps;

public class ItemManager {

    private static Map<Integer, ItemDefinition> furnitureIDs;
    private static Map<Integer, ItemDefinition> furnitureSpriteIDs;

    public static void load() {
        furnitureIDs = ItemDao.getFurniture();
        furnitureSpriteIDs = Maps.newHashMap();

        for (Entry<Integer, ItemDefinition> set : furnitureIDs.entrySet()) {
            furnitureSpriteIDs.put(set.getValue().getSpriteID(), set.getValue());
        }
    }

    public static ItemDefinition getFurnitureByID(int id) {

        if (furnitureIDs.containsKey(id)) {
            return furnitureIDs.get(id);
        }

        return null;
    }

    public static ItemDefinition getFurnitureBySpriteID(int id) {

        if (furnitureIDs.containsKey(id)) {
            return furnitureIDs.get(id);
        }

        return null;
    }
}
