package org.alexdev.icarus.messages.outgoing.camera;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class PhotoPriceComposer extends MessageComposer {

    private int costCredits;
    private int costPixels;
    private int costWeb;

    public PhotoPriceComposer(int costCredits, int costPixels) {
        this.costCredits = costCredits;
        this.costPixels = costPixels;
        this.costWeb = 1000;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.costCredits);
        response.writeInt(this.costPixels);
        response.writeInt(this.costWeb);
    }

    @Override
    public short getHeader() {
        return Outgoing.PhotoPriceComposer;
    }
}