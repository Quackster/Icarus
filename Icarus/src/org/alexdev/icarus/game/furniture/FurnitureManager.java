package org.alexdev.icarus.game.furniture;

import java.util.Map;
import java.util.Map.Entry;
import org.alexdev.icarus.dao.mysql.ItemDao;

import com.google.common.collect.Maps;

public class FurnitureManager {

    private static Map<Integer, ItemDefinition> furnitureIds;
    private static Map<Integer, ItemDefinition> furnitureSpriteIds;

    public static void load() {
        furnitureIds = ItemDao.getFurniture();
        furnitureSpriteIds = Maps.newHashMap();

        for (Entry<Integer, ItemDefinition> set : furnitureIds.entrySet()) {
            furnitureSpriteIds.put(set.getValue().getSpriteId(), set.getValue());
        }
        
        String test = "";
    }

    public static ItemDefinition getFurnitureById(int id) {

        if (furnitureIds.containsKey(id)) {
            return furnitureIds.get(id);
        }

        return null;
    }

    public static ItemDefinition getFurnitureBySpriteId(int id) {

        if (furnitureIds.containsKey(id)) {
            return furnitureIds.get(id);
        }

        return null;
    }
}
