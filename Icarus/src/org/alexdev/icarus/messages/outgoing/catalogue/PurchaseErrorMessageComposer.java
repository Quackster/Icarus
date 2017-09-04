package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class PurchaseErrorMessageComposer extends MessageComposer {

    private boolean creditsError;
    private boolean pixelError;

    public PurchaseErrorMessageComposer(boolean creditsError, boolean pixelError) {
        this.creditsError = creditsError;
        this.pixelError = pixelError;
    }

    @Override
    public void write() {
        
        response.init(Outgoing.PurchaseErrorMessageComposer);
        response.writeBool(creditsError);
        response.writeBool(pixelError);
        
    }

}
