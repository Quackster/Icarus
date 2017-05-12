package org.alexdev.icarus.game.inventory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.InventoryDao;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.item.FurniListNotificationComposer;
import org.alexdev.icarus.messages.outgoing.item.InventoryLoadMessageComposer;
import org.alexdev.icarus.messages.outgoing.item.RemoveInventoryItemComposer;
import org.alexdev.icarus.messages.outgoing.item.UpdateInventoryMessageComposer;

public class Inventory {

    private boolean initalised;
    private Player player;
    private Map<Integer, Item> items;

    public Inventory(Player player) {
        this.initalised = false;
        this.player = player;
    }

    public void init() {
        if (!this.initalised) {
            this.items = InventoryDao.getInventoryItems(this.player.getDetails().getId());
            this.initalised = true;
        }
    }

    public void addItem(Item item) {
        this.items.put(item.getId(), item);
        this.player.send(new FurniListNotificationComposer(item.getId(), 1));
    }

    public void remove(Item item) {
        this.items.remove(item.getId());
        this.player.send(new RemoveInventoryItemComposer(item.getId()));
    }

    public void update() {
        this.player.send(new UpdateInventoryMessageComposer());
        this.player.send(new InventoryLoadMessageComposer(this.player));
    }

    public Item getItem(int id) {

        if (this.items.containsKey(id)) {
            return this.items.get(id);
        }

        return null;
    }



    public void dispose() {

        if (this.items != null) {
            this.items.clear();
            this.items = null;
        }
    }

    public boolean isInitalised() {
        return initalised;
    }

    public void setInitalised(boolean initalised) {
        this.initalised = initalised;
    }

    public Map<Integer, Item> getItsems() {
        return items;
    }

    public List<Item> getFloorItems() {
        return items.values().stream().filter(item -> item != null && item.getType() == ItemType.FLOOR).collect(Collectors.toList());
    }

    public List<Item> getWallItems() {
        return items.values().stream().filter(item -> item != null && item.getType() == ItemType.WALL).collect(Collectors.toList());
    }
}
