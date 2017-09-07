package org.alexdev.icarus.messages.incoming.pets;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PlacePetMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Pet pet = player.getInventory().getPet(reader.readInt());
        
        int X = reader.readInt();
        int Y = reader.readInt();
        
        player.getRoom().addEntity(pet, X, Y, 4);
        
        player.getInventory().remove(pet);
        player.getInventory().updatePets();
    }
}
