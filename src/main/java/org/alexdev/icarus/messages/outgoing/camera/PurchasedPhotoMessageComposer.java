package org.alexdev.icarus.messages.outgoing.camera;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class PurchasedPhotoMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
    }

    public short getHeader() {
        return Outgoing.PurchasedPhotoComposer;
    }
}
