package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class UniqueMachineIDMessageComposer extends MessageComposer {

    private String uniqueMachineId;

    public UniqueMachineIDMessageComposer(String uniqueMachineId) {
        this.uniqueMachineId = uniqueMachineId == null ? "" : uniqueMachineId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.UniqueMachineIdMessageComposer);
        this.response.writeString(this.uniqueMachineId);
    }
}
