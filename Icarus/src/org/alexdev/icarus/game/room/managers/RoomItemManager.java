package org.alexdev.icarus.game.room.managers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.room.Room;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RoomItemManager {

    private Room room;
    private Map<Integer, Item> items;
    
    public RoomItemManager(Room room) {
        this.room = room;
        this.items = Maps.newHashMap();
    }
    
    public List<Item> getFloorItems() {
        return items.values().stream().filter(item -> item.getType() == ItemType.FLOOR).collect(Collectors.toList());
    }

    public List<Item> getWallItems() {
        return items.values().stream().filter(item -> item.getType() == ItemType.WALL).collect(Collectors.toList());
    }

    public List<Item> getItems(InteractionType interactionType) {

        try {
            
            return items.values().stream()
                    .filter(item -> item.getDefinition().getInteractionType() == interactionType)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public Item getItem(int itemId) {

        if (this.items.containsKey(itemId)) {
            return this.items.get(itemId);
        }

        return ItemDao.getItem(itemId);
    }

    public void refreshRoomFurniture() {
        
        if (this.items != null) {
            this.items.clear();
            this.items = null;
        }
        
        this.items = ItemDao.getRoomItems(room.getData().getId());
    } 
    
    public Map<Integer, Item> getItems() {
        return items;
    }
}
