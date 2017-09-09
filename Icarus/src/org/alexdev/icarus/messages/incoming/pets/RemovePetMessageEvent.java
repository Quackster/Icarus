package org.alexdev.icarus.messages.incoming.pets;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RemovePetMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Entity entity = player.getRoom().getEntityByID(reader.readInt());
        
        if (entity == null) {
            return;
        }
        
        if (entity.getType() != EntityType.PET) {
            return;
        }
        
        Pet pet = (Pet)entity;
        player.getRoom().removeEntity(pet);
        
        boolean isPetOwner = player.getDetails().getID() == pet.getOwnerID();
        
        if (isPetOwner) {
            
            player.getInventory().addPet(pet);
            player.getInventory().updatePets();
            
            pet.setRoomID(0);
            pet.save();
        
        } else {
            
            boolean isPetOwnerOnline = PlayerManager.getByID(pet.getOwnerID()) != null;
            
            pet.setRoomID(0);
            pet.save();
            
            if (isPetOwnerOnline) {
                
                Player petOwner = PlayerManager.getByID(pet.getOwnerID());
                
                petOwner.getInventory().addPet(pet);
                petOwner.getInventory().updatePets();
            }
        }
    }
}
