package org.alexdev.icarus.messages.outgoing.pets;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Util;

public class PetInformationComposer extends MessageComposer {

    private Pet pet;

    public PetInformationComposer(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.PetInformationComposer);
        this.response.writeInt(this.pet.getId());
        this.response.writeString(this.pet.getName());
        this.response.writeInt(this.pet.getLevel());
        this.response.writeInt(20); // max level
        this.response.writeInt(this.pet.getExperience());
        this.response.writeInt(200); // experience goal
        this.response.writeInt(this.pet.getEnergy());
        this.response.writeInt(100); // max energy
        this.response.writeInt(100); // nutrition
        this.response.writeInt(100); // max nutrition
        this.response.writeInt(this.pet.getScratches());
        this.response.writeInt(this.pet.getOwnerId());
        this.response.writeInt((Util.getCurrentTimeSeconds() - this.pet.getBirthday()) / 60 / 60 / 24);
        this.response.writeString(this.pet.getOwnerName());
        this.response.writeInt(0);
        this.response.writeBool(this.pet.isSaddled());
        this.response.writeBool(this.pet.hasRider());
        this.response.writeInt(0);
        this.response.writeInt(this.pet.isAnyRider() ? 1 : 0);
        this.response.writeBool(false);
        this.response.writeBool(true);
        this.response.writeBool(false);
        this.response.writeInt(0);
        this.response.writeInt(129600);
        this.response.writeInt(128000);
        this.response.writeInt(1000);
        this.response.writeBool(true);
    }
}
