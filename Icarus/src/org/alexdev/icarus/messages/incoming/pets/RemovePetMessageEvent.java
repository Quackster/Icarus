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
        
        Entity entity = player.getRoom().getEntityManager().getEntityById(reader.readInt());
        
        if (entity == null) {
            return;
        }
        
        if (entity.getType() != EntityType.PET) {
            return;
        }
        
        player.getRoom().getEntityManager().removeEntity(entity);
        
        Pet pet = (Pet)entity;
        
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
