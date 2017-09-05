package org.alexdev.icarus.messages.outgoing.pets;

import java.util.Map;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class PetInventoryMessageComposer extends MessageComposer {

    private Map<Integer, Pet> pets;

    public PetInventoryMessageComposer(Map<Integer, Pet> pets) {
        this.pets = pets;
    }


    @Override
    public void write() {
        
        this.response.init(Outgoing.PetInventoryMessageComposer);
        this.response.writeInt(1);
        this.response.writeInt(1);

        this.response.writeInt(pets.size());

        for (Pet data : pets.values()) {
            this.response.writeInt(data.getId());
            this.response.writeString(data.getName());
            this.response.writeInt(data.getTypeId());
            this.response.writeInt(data.getRaceId());
            this.response.writeString(data.getColour());
            this.response.writeInt(0);
            this.response.writeInt(0);
            this.response.writeInt(0);
        }
    }
}
