package org.alexdev.icarus.messages.incoming.pets;

import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PlacePetMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        if (!player.getRoom().getData().isAllowPets() && !player.getRoom().hasRights(player.getDetails().getId(), true)) {
            return;
        }
        
        Pet pet = player.getInventory().getPet(reader.readInt());
        
        if (pet == null) {
            return;
        }
        
        int X = reader.readInt();
        int Y = reader.readInt();
        
        if (X == 0 || Y == 0) {
            Position position = player.getRoom().getModel().getDoorLocation();
            X = position.getX();
            Y = position.getY();
        }
        
        player.getRoom().getEntityManager().addEntity(pet, X, Y, 4);
        
        player.getInventory().remove(pet);
        player.getInventory().updatePets();
        
        pet.setRoomId(player.getRoom().getData().getId());
        
        pet.save();
        pet.savePosition();
    }
}
