package org.alexdev.icarus.messages.outgoing;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

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
    public void write() {
        this.response.init(Outgoing.PhotoPriceComposer);
        this.response.writeInt(this.costCredits);
        this.response.writeInt(this.costPixels);
        this.response.writeInt(this.costWeb);
    }

}
