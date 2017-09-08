package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class PresentDeliverErrorMessageComposer extends MessageComposer {

    private boolean creditsError;
    private boolean ducketError;

    public PresentDeliverErrorMessageComposer(boolean creditsError, boolean ducketError) {
        this.creditsError = creditsError;
        this.ducketError = ducketError;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.PresentDeliverErrorMessageComposer);
        this.response.writeInt(this.creditsError);
        this.response.writeInt(this.ducketError);
    }
}
