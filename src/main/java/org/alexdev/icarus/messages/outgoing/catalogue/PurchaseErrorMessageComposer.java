package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class PurchaseErrorMessageComposer extends MessageComposer {

    private boolean creditsError;
    private boolean pixelError;

    public PurchaseErrorMessageComposer(boolean creditsError, boolean pixelError) {
        this.creditsError = creditsError;
        this.pixelError = pixelError;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.PurchaseErrorMessageComposer);
        this.response.writeBool(creditsError);
        this.response.writeBool(pixelError);
        
    }
}
