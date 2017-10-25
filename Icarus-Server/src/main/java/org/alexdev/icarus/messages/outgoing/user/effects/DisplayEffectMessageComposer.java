package org.alexdev.icarus.messages.outgoing.user.effects;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class DisplayEffectMessageComposer extends MessageComposer {

    private int virtualId;
    private int effectId;

    public DisplayEffectMessageComposer(int virtualId, int effectId) {
        this.virtualId = virtualId;
        this.effectId = effectId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.DisplayEffectMessageComposer);
        response.writeInt(this.virtualId);
        response.writeInt(this.effectId);
        response.writeInt(0);
    }

    @Override
    public short getHeader() {
        return Outgoing.DisplayEffectMessageComposer;
    }
}
