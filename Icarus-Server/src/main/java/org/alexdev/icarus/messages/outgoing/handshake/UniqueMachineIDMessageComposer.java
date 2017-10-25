package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UniqueMachineIDMessageComposer extends MessageComposer {

    private String uniqueMachineId;

    public UniqueMachineIDMessageComposer(String uniqueMachineId) {
        this.uniqueMachineId = uniqueMachineId == null ? "" : uniqueMachineId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UniqueMachineIDMessageComposer);
        response.writeString(this.uniqueMachineId);
    }

    @Override
    public short getHeader() {
        return Outgoing.UniqueMachineIDMessageComposer;
    }
}