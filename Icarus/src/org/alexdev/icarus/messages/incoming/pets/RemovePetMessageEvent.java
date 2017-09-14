package org.alexdev.icarus.messages.incoming.pets;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RemovePetMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        Room room = player.getRoom();
        
        if (room == null) {
            return;
        }
        
        Pet pet = room.getEntityManager().getEntityById(reader.readInt(), Pet.class);
        
        if (pet == null) {
            return;
        }
        
        player.getRoom().getEntityManager().removeEntity(pet);

        boolean isPetOwner = player.getDetails().getId() == pet.getOwnerId();
        
        if (isPetOwner) {
            
            player.getInventory().addPet(pet);
            player.getInventory().updatePets();
            
            pet.setRoomId(0);
            pet.save();
        
        } else {
            
            boolean isPetOwnerOnline = PlayerManager.getById(pet.getOwnerId()) != null;
            
            pet.setRoomId(0);
            pet.save();
            
            if (isPetOwnerOnline) {
                
                Player petOwner = PlayerManager.getById(pet.getOwnerId());
                
                petOwner.getInventory().addPet(pet);
                petOwner.getInventory().updatePets();
            }
        }
    }
}
