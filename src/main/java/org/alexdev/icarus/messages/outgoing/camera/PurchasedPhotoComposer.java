package org.alexdev.icarus.messages.outgoing.camera;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class PurchasedPhotoComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.PurchasedPhotoComposer);
    }

}
