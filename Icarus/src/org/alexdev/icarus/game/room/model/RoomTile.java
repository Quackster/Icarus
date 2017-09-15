package org.alexdev.icarus.game.room.model;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;

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
    
    public Room getRoom() {
        return room;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
    
    public List<Item> getItems() {
        return items;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        
        if (new Position(x, y).isMatch(this.room.getModel().getDoorLocation())) {
            return; // Don't override door otherwise people will get stuck
        }
        
        this.entity = entity;
    }
    
    public boolean isHeightValid(double height) {
        
        for (int i = 0; i < this.items.size(); i++) {          
            Item item = this.items.get(i);
            if (item.getPosition().getZ() < height) {
                return false;
            }
        }
        
        return true;
    }

    public Item getHighestItem() {
        return highestItem;
    }

    public void setHighestItem(Item highestItem) {
        this.highestItem = highestItem;
    }

    public boolean hasOverrideLock() {
        return overrideLock;
    }

    public void setOverrideLock(boolean overrideLock) {
        this.overrideLock = overrideLock;
    }

    public Item getItemUnderneath() {
        return itemUnderneath;
    }

    public void setItemUnderneath(Item itemUnderneath) {
        this.itemUnderneath = itemUnderneath;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
