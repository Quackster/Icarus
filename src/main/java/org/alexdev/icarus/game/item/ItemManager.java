package org.alexdev.icarus.game.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemManager {

    private static Map<Integer, ItemDefinition> furnitureIds;

    private static final Logger log = LoggerFactory.getLogger(ItemManager.class);

    /**
     * Load all item definitions.
     */
    public static void load() {
        furnitureIds = ItemDao.getFurniture();

        if (Util.getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} item definitions", furnitureIds.size());
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
}
