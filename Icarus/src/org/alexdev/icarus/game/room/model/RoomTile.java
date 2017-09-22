package org.alexdev.icarus.game.room.model;

import java.util.Set;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;

import com.google.common.collect.Sets;

public class RoomTile {

    private double height = 0;
    private boolean overrideLock = false;
    
    private Set<Item> items;
    private Set<Entity> entities;
    
    private Room room;
    private Item highestItem = null;
    private Item itemUnderneath = null;

    private int x;
    private int y;
    
    public RoomTile(Room room, int x, int y) {
        this.room = room;
        this.x = x;
        this.y = y;
        this.reset();
    }
    
    public void reset() {
        this.height = this.room.getModel().getHeight(x, y);
        this.items = Sets.newHashSet();
        this.entities = Sets.newHashSet();
    }
    
    /**
     * Gets the room.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the new height
     */
    public void setHeight(double height) {
        this.height = height;
    }
    
    /**
     * Gets the items.
     *
     * @return the items
     */
    public Set<Item> getItems() {
        return items;
    }

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    public Set<Entity> getEntities() {
        return entities;
    }

    /**
     * Sets the entity.
     *
     * @param entity the new entity
     */
    public void addEntity(Entity entity) {
        
        if (new Position(this.x, this.y).isMatch(this.room.getModel().getDoorLocation())) {
            return;
        }
        
        this.entities.add(entity);
    }
    
    /**
     * Contains the entity.
     *
     * @param entity the entity
     * @return true, if successful
     */
    public boolean containsEntity(Entity entity) {
        
        return this.entities.contains(entity);
    }

    /**
     * Removes the entity.
     *
     * @param entity the entity
     * @return true, if successful
     */
    public boolean removeEntity(Entity entity) {
        
        return this.entities.remove(entity);
    }

    
    /**
     * Gets the highest item.
     *
     * @return the highest item
     */
    public Item getHighestItem() {
        return highestItem;
    }

    /**
     * Sets the highest item.
     *
     * @param highestItem the new highest item
     */
    public void setHighestItem(Item highestItem) {
        this.highestItem = highestItem;
    }

    /**
     * Checks for override lock.
     *
     * @return true, if successful
     */
    public boolean hasOverrideLock() {
        return overrideLock;
    }

    /**
     * Sets the override lock.
     *
     * @param overrideLock the new override lock
     */
    public void setOverrideLock(boolean overrideLock) {
        this.overrideLock = overrideLock;
    }

    /**
     * Gets the item underneath.
     *
     * @return the item underneath
     */
    public Item getItemUnderneath() {
        return itemUnderneath;
    }

    /**
     * Sets the item underneath.
     *
     * @param itemUnderneath the new item underneath
     */
    public void setItemUnderneath(Item itemUnderneath) {
        this.itemUnderneath = itemUnderneath;
    }

    /**
     * Gets the x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }
}
