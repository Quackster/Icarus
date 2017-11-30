package org.alexdev.icarus.game.item;

import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private Map<Integer, ItemDefinition> furniture;
    private Map<String, ItemDefinition> furnitureClassLookup;

    private static final Logger log = LoggerFactory.getLogger(ItemManager.class);
    private static ItemManager instance;

    public ItemManager() {
        this.reload();
    }

    /**
     * Reloads all definitions from database
     */
    public void reload() {
        this.furniture = ItemDao.getFurniture();
        this.furnitureClassLookup = new HashMap<>();

        for (ItemDefinition def : this.furniture.values()) {
            this.furnitureClassLookup.put(def.getItemName(), def);
        }

        if (Configuration.getInstance().getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} item definitions", furniture.size());
        }
    }

    /**
     * Gets the furniture by id.
     *
     * @param id the id
     * @return the furniture by id
     */
    public ItemDefinition getFurnitureById(int id) {

        if (this.furniture.containsKey(id)) {
            return this.furniture.get(id);
        }

        return null;
    }

    /**
     * Find furniture definition by swf class.
     *
     * @param className the swf file name
     * @return the furniture by class
     */
    public ItemDefinition getFurnitureByClass(String className) {

        if (this.furnitureClassLookup.containsKey(className)) {
            return this.furnitureClassLookup.get(className);
        }

        return null;
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static ItemManager getInstance() {

        if (instance == null) {
            instance = new ItemManager();
        }

        return instance;
    }
}
