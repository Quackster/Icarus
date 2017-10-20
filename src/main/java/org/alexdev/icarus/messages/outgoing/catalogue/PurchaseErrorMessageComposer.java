package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class PurchaseErrorMessageComposer extends MessageComposer {

    private boolean creditsError;
    private boolean pixelError;

    public PurchaseErrorMessageComposer(boolean creditsError, boolean pixelError) {
        this.creditsError = creditsError;
        this.pixelError = pixelError;
    }

    @Override
    public void compose(Response response) {
        response.writeBool(creditsError);
        response.writeBool(pixelError);
    }

    @Override
    public short getHeader() {
        return Outgoing.PurchaseErrorMessageComposer;
    }
}