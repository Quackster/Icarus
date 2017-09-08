package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class UpdateInventoryMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.UpdateInventoryMessageComposer);
        
    }
}
