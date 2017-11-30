package org.alexdev.icarus.game.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.item.InventoryDao;
import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.game.inventory.effects.EffectManager;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.item.UnseenItemsNotificationComposer;
import org.alexdev.icarus.messages.outgoing.item.InventoryLoadMessageComposer;
import org.alexdev.icarus.messages.outgoing.item.RemoveInventoryItemComposer;
import org.alexdev.icarus.messages.outgoing.item.UpdateInventoryMessageComposer;
import org.alexdev.icarus.messages.outgoing.pets.PetInventoryMessageComposer;
import org.alexdev.icarus.messages.types.MessageComposer;

public class Inventory {

    private Player player;
    private EffectManager effectManager;

    private Map<Integer, Pet> pets;
    private Map<Integer, Item> items;

    public Inventory(Player player) {
        this.player = player;
    }

    /**
     * Initiates the inventory items
     */
    public void init() {
        /*this.effects = new ArrayList<>();
        
        Effect effect = new Effect(2, 1000, true, 1000, 10);
        this.effects.add(effect);*/

        this.effectManager = new EffectManager(this.player);
        this.items = InventoryDao.getInventoryItems(this.player.getEntityId());
        this.pets = InventoryDao.getInventoryPets(this.player.getEntityId());
    }

    /**
     * Adds the item.
     *
     * @param item the item
     * @param notification the notification
     */
    public void addItem(Item item, InventoryNotification notification) {
        addItem(item, notification, false);
    }

    /**
     * Adds the item but with queue packet option.
     *
     * @param item the item
     * @param notification the notification
     */
    public void addItem(Item item, InventoryNotification notification, boolean queuePacket) {
        this.items.put(item.getId(), item);

        if (notification == InventoryNotification.ALERT) {
            MessageComposer composer = new UnseenItemsNotificationComposer(item.getId(), 1);
            if (queuePacket) {
                this.player.sendQueued(composer);
            } else {
                this.player.send(composer);
            }
        }
    }

    /**
     * Removes the.
     *
     * @param item the item
     */
    public void remove(Item item) {
        this.items.remove(item.getId());
        this.player.send(new RemoveInventoryItemComposer(item.getId()));
    }

    /**
     * Adds the pet.
     *
     * @param pet the pet
     * @param notification the notification
     */
    public void addPet(Pet pet, InventoryNotification notification) {
        this.pets.put(pet.getId(), pet);

        if (notification == InventoryNotification.ALERT) {
            this.player.send(new UnseenItemsNotificationComposer(pet.getId(), 3));
        }
    }

    /**
     * Removes the.
     *
     * @param pet the pet
     */
    public void remove(Pet pet) {
        this.pets.remove(pet.getId());
        this.player.send(new RemoveInventoryItemComposer(pet.getId()));
    }

    /**
     * Update items.
     */
    public void updateItems() {
        this.player.sendQueued(new UpdateInventoryMessageComposer());
        this.player.sendQueued(new InventoryLoadMessageComposer(this.items.values()));
        this.player.flushQueue();
    }

    /**
     * Update pets.
     */
    public void updatePets() {
        this.player.sendQueued(new UpdateInventoryMessageComposer());
        this.player.sendQueued(new PetInventoryMessageComposer(this.pets));
        this.player.flushQueue();
    }

    /**
     * Gets the item.
     *
     * @param id the id
     * @return the item
     */
    public Item getItem(int id) {
        if (this.items.containsKey(id)) {
            return this.items.get(id);
        }

        return null;
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
     * Gets the floor items.
     *
     * @return the floor items
     */
    public List<Item> getFloorItems() {
        return items.values().stream().filter(item -> item != null && item.getDefinition().getType() == ItemType.FLOOR).collect(Collectors.toList());
    }

    /**
     * Gets the pets.
     *
     * @return the pets
     */
    public Map<Integer, Pet> getPets() {
        return pets;
    }

    /**
     * Gets the wall items.
     *
     * @return the wall items
     */
    public List<Item> getWallItems() {
        return items.values().stream().filter(item -> item != null && item.getDefinition().getType() == ItemType.WALL).collect(Collectors.toList());
    }

    /**
     * Gets the pet.
     *
     * @param id the id
     * @return the pet
     */
    public Pet getPet(int id) {
        return this.pets.get(id);
    }

    /**
     * Get the effect manager.
     *
     * @return the effect manager
     */
    public EffectManager getEffectManager() {
        return effectManager;
    }
}
