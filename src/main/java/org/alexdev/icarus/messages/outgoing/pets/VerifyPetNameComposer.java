package org.alexdev.icarus.messages.outgoing.pets;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class VerifyPetNameComposer extends MessageComposer {

    private int error;
    private String extraData;

    public VerifyPetNameComposer(int error, String extraData) {
        this.error = error;
        this.extraData = extraData;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.VerifyPetNameComposer);
        response.writeInt(this.error);
        response.writeString(this.extraData);
    }

    @Override
    public short getHeader() {
        return Outgoing.VerifyPetNameComposer;
    }
}
