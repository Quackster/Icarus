package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class UniqueMachineIDMessageComposer extends MessageComposer {

    private String uniqueMachineId;

    public UniqueMachineIDMessageComposer(String uniqueMachineId) {
        this.uniqueMachineId = uniqueMachineId;
    }

    @Override
    public void write() {
        
        if (this.uniqueMachineId == null) {
            return;
        }
        
        this.response.init(Outgoing.UniqueMachineIDMessageComposer);
        this.response.writeString(this.uniqueMachineId);
    }
}
