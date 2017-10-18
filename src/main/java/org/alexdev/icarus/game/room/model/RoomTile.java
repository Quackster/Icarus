package org.alexdev.icarus.game.room.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;

public class RoomTile {

    private double height = 0;
    private boolean overrideLock = false;
    
    private List<Item> items;
    private List<Entity> entities;
    
    private Room room;
    private Item highestItem = null;

    private int x;
    private int y;
    
    public RoomTile(Room room, int x, int y) {
        this.room = room;
        this.x = x;
        this.y = y;
        this.reset();
    }
    
    /**
     * Reset the tile, will apply height and wipe any existing items
     * or entities.
     */
    public void reset() {
        this.height = this.room.getModel().getHeight(this.x, this.y);
        this.items = new ArrayList<>();
        this.entities = new ArrayList<>();
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
    public List<Item> getItems() {
        return items;
    }

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Sets the entity.
     *
     * @param entity the new entity
     */
    public void addEntity(Entity entity) {
        
        if (new Position(this.x, this.y).equals(this.room.getModel().getDoorLocation())) {
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
