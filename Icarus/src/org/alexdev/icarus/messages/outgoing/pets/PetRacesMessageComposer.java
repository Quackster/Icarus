package org.alexdev.icarus.messages.outgoing.pets;

import java.util.List;

import org.alexdev.icarus.game.pets.PetRace;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class PetRacesMessageComposer extends MessageComposer {

    private String petRace;
    private List<PetRace> races;

    public PetRacesMessageComposer(String petRace, List<PetRace> races) {
        this.petRace = petRace;
        this.races = races;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.PetRacesMessageComposer);
        this.response.writeString(this.petRace);
        this.response.writeInt(this.races.size());

        for (PetRace race : this.races) {
            this.response.writeInt(race.getRaceId());
            this.response.writeInt(race.getColour1());
            this.response.writeInt(race.getColour2());
            this.response.writeBool(race.hasColour1());
            this.response.writeBool(race.hasColour2());
        }
    }
}
