package org.alexdev.icarus.messages.outgoing.pets;

import java.util.List;

import org.alexdev.icarus.game.pets.PetRace;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class PetRacesMessageComposer extends MessageComposer {

    private String petRace;
    private List<PetRace> races;

    public PetRacesMessageComposer(String petRace, List<PetRace> races) {
        this.petRace = petRace;
        this.races = races;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.PetRacesMessageComposer);
        response.writeString(this.petRace);
        response.writeInt(this.races.size());

        for (PetRace race : this.races) {
            response.writeInt(race.getRaceId());
            response.writeInt(race.getColour1());
            response.writeInt(race.getColour2());
            response.writeBool(race.hasColour1());
            response.writeBool(race.hasColour2());
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.PetRacesMessageComposer;
    }
}
