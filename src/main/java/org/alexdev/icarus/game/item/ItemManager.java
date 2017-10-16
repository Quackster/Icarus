package org.alexdev.icarus.game.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemManager {

    private static Map<Integer, ItemDefinition> furnitures;
    private static Map<String, ItemDefinition> furnitureClassLookup;

    private static final Logger log = LoggerFactory.getLogger(ItemManager.class);

    /**
     * Load all item definitions.
     */
    public static void load() {
        furnitures = ItemDao.getFurniture();
        furnitureClassLookup = new HashMap<>();

        for (ItemDefinition def : furnitures.values()) {
            furnitureClassLookup.put(def.getItemName(), def);
        }

        if (Util.getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} item definitions", furnitures.size());
        }
    }

    /**
     * Gets the furniture by id.
     *
     * @param id the id
     * @return the furniture by id
     */
    public static ItemDefinition getFurnitureById(int id) {

        if (furnitures.containsKey(id)) {
            return furnitures.get(id);
        }

        return null;
    }

    /**
     * Find furniture definition by swf class.
     *
     * @param className the swf file name
     * @return the furniture by class
     */
    public static ItemDefinition getFurnitureByClass(String className) {

        if (furnitureClassLookup.containsKey(className)) {
            return furnitureClassLookup.get(className);
        }

        return null;
    }
}
