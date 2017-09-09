package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class UniqueMachineIDMessageComposer extends MessageComposer {

    private String uniqueMachineID;

    public UniqueMachineIDMessageComposer(String uniqueMachineID) {
        this.uniqueMachineID = uniqueMachineID;
    }

    @Override
    public void write() {
        
        if (this.uniqueMachineID == null) {
            return;
        }
        
        this.response.init(Outgoing.UniqueMachineIDMessageComposer);
        this.response.writeString(this.uniqueMachineID);
    }
}
