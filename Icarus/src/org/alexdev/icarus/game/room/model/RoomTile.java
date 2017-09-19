package org.alexdev.icarus.game.room.model;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.util.GameSettings;

import com.google.common.collect.Lists;

public class RoomTile {

    private double height = 0;
    private boolean overrideLock = false;
    
    private List<Item> items;
    private Room room;
    
    private Item highestItem = null;
    private Item itemUnderneath = null;
    
    private Entity entity;
    
    private int x;
    private int y;
    
    public RoomTile(Room room, int x, int y) {
        this.room = room;
        this.x = x;
        this.y = y;
        this.height = this.room.getModel().getHeight(x, y);
        this.items = Lists.newArrayList();
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
    public Entity getEntity() {
        return entity;
    }

    /**
     * Sets the entity.
     *
     * @param entity the new entity
     */
    public void setEntity(Entity entity) {
        
        if (new Position(x, y).isMatch(this.room.getModel().getDoorLocation())) {
            return; // Don't override door otherwise people will get stuck
        }
        
        this.entity = entity;
    }
    

    /**
     * Checks if is height valid.
     *
     * @return true, if is height valid
     */
    public boolean isHeightValid() {
        return this.isHeightValid(GameSettings.MINIMUM_WALK_UNDER_HEIGHT);
    }
    
    /**
     * Checks if is height valid.
     *
     * @param height the height
     * @return true, if is height valid
     */
    public boolean isHeightValid(double height) {
        
        for (int i = 0; i < this.items.size(); i++) {    
            
            Item item = this.items.get(i);
            
            if (item.getPosition().getZ() < height) {
                return false;
            }
        }
        
        return false;
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
