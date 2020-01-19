package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.item.InventoryLoadMessageComposer;
import org.alexdev.icarus.messages.outgoing.pets.PetInventoryMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class InventoryMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        Inventory inventory = player.getInventory();
        
        if (inventory == null) {
            return;
        }
        
        player.send(new InventoryLoadMessageComposer(inventory.getItems().values()));
        player.send(new PetInventoryMessageComposer(inventory.getPets()));
    }
}