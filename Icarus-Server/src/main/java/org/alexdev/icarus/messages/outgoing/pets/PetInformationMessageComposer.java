package org.alexdev.icarus.messages.outgoing.pets;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.util.Util;
import org.alexdev.icarus.util.date.DateUtil;

public class PetInformationMessageComposer extends MessageComposer {

    private Pet pet;

    public PetInformationMessageComposer(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.PetInformationMessageComposer);
        response.writeInt(this.pet.getId());
        response.writeString(this.pet.getName());
        response.writeInt(this.pet.getLevel());
        response.writeInt(20); // max level
        response.writeInt(this.pet.getExperience());
        response.writeInt(200); // experience goal
        response.writeInt(this.pet.getEnergy());
        response.writeInt(100); // max energy
        response.writeInt(100); // nutrition
        response.writeInt(100); // max nutrition
        response.writeInt(this.pet.getScratches());
        response.writeInt(this.pet.getOwnerId());
        response.writeInt((DateUtil.getCurrentTimeSeconds() - this.pet.getBirthday()) / 60 / 60 / 24);
        response.writeString(this.pet.getOwnerName());
        response.writeInt(0);
        response.writeBool(this.pet.isSaddled());
        response.writeBool(this.pet.hasRider());
        response.writeInt(0);
        response.writeInt(this.pet.isAnyRider() ? 1 : 0);
        response.writeBool(false);
        response.writeBool(true);
        response.writeBool(false);
        response.writeInt(0);
        response.writeInt(129600);
        response.writeInt(128000);
        response.writeInt(1000);
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return Outgoing.PetInformationMessageComposer;
    }
}