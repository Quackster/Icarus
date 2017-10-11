package org.alexdev.icarus.messages.outgoing.user.effects;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class EffectMessageComposer extends MessageComposer {
    
    private int virtualId;
    private int effectId;

    public EffectMessageComposer(int virtualId, int effectId) {
        this.virtualId = virtualId;
        this.effectId = effectId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.EffectMessageComposer);
        this.response.writeInt(this.virtualId);
        this.response.writeInt(this.effectId);
        this.response.writeInt(0);
    }
}
