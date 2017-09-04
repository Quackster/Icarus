package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class UpdateInventoryMessageComposer extends MessageComposer {

    @Override
    public void write() {
        response.init(Outgoing.UpdateInventoryMessageComposer);
        
    }
}
