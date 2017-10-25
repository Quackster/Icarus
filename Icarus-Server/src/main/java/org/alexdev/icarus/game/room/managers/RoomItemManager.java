package org.alexdev.icarus.game.room.managers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.room.Room;

public class RoomItemManager {

    private boolean hasRollers;
    private Room room;

    private Map<Integer, Item> items;
    private List<Item> rollers;

    public RoomItemManager(Room room) {
        this.room = room;
        this.items = new HashMap<>();
    }
    
    /**
     * Gets the floor items.
     *
     * @return the floor items
     */
    public List<Item> getFloorItems() {
        return items.values().stream().filter(item -> item.getDefinition().getType() == ItemType.FLOOR).collect(Collectors.toList());
    }

    /**
     * Gets the wall items.
     *
     * @return the wall items
     */
    public List<Item> getWallItems() {
        return items.values().stream().filter(item -> item.getDefinition().getType() == ItemType.WALL).collect(Collectors.toList());
    }

    /**
     * Gets the items by interaction type.
     *
     * @param interactionType the interaction type
     * @return the items
     */
    public List<Item> getItems(InteractionType interactionType) {

        try {
            Stream<Item> items = this.items.values().stream().filter(item -> item.getDefinition().getInteractionType() == interactionType);
            return items.collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>(); // could not find any of that type, stream throws exception then
        }
    }


    /**
     * Update the room has rollers variable
     */
    public void refreshHasRollers() {
        this.rollers = this.getItems(InteractionType.ROLLER);
        this.hasRollers = rollers.size() > 0;
    }


    /**
     * Gets the item, will try and find from database if the Item is
     * not loaded, the item loaded from the database may not be from the same room as
     * the room defined in this instance.
     *
     * @param itemId the item id
     * @return the item
     */
    public Item getItem(int itemId) {

        if (this.items.containsKey(itemId)) {
            return this.items.get(itemId);
        }

        return ItemDao.getItem(itemId);
    }

    /**
     * Refresh room furniture.
     */
    public void refreshRoomFurniture() {
        
        if (this.items != null) {
            this.items.clear();
        }
        
        this.items = ItemDao.getRoomItems(this.room.getData().getId());
    } 
    
    /**
     * Gets the items.
     *
     * @return the items
     */
    public Map<Integer, Item> getItems() {
        return items;
    }

    /**
     * Does the room have rollers anywhere.
     *
     * @return true, if successful
     */
    public boolean hasRollers() {
        return hasRollers;
    }

    /**
     * Get the rooms rollers
     *
     * @return the rollers
     */
    public List<Item> getRollers() {
        return rollers;
    }
}
