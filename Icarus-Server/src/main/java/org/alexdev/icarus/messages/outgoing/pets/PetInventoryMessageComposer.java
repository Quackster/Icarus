package org.alexdev.icarus.messages.outgoing.pets;

import java.util.Map;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class PetInventoryMessageComposer extends MessageComposer {

    private Map<Integer, Pet> pets;

    public PetInventoryMessageComposer(Map<Integer, Pet> pets) {
        this.pets = pets;
    }


    @Override
    public void compose(Response response) {

        //response.init(Outgoing.PetInventoryMessageComposer);
        response.writeInt(1);
        response.writeInt(1);

        response.writeInt(pets.size());

        for (Pet data : pets.values()) {
            response.writeInt(data.getId());
            response.writeString(data.getName());
            response.writeInt(data.getTypeId());
            response.writeInt(data.getRaceId());
            response.writeString(data.getColour());
            response.writeInt(0);
            response.writeInt(0);
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.PetInventoryMessageComposer;
    }
}
