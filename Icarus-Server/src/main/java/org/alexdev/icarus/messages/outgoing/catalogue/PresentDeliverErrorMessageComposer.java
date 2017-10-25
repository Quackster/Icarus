package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class PresentDeliverErrorMessageComposer extends MessageComposer {

    private boolean creditsError;
    private boolean ducketError;

    public PresentDeliverErrorMessageComposer(boolean creditsError, boolean ducketError) {
        this.creditsError = creditsError;
        this.ducketError = ducketError;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.creditsError);
        response.writeInt(this.ducketError);
    }

    public short getHeader() {
        return Outgoing.PresentDeliverErrorMessageComposer;
    }
}