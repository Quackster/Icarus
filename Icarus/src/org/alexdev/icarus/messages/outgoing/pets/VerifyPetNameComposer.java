package org.alexdev.icarus.messages.outgoing.pets;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class VerifyPetNameComposer extends MessageComposer {

    private int error;
    private String extraData;

    public VerifyPetNameComposer(int error, String extraData) {
        this.error = error;
        this.extraData = extraData;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.VerifyPetNameComposer);
        this.response.writeInt(this.error);
        this.response.writeString(this.extraData);
    }

}
