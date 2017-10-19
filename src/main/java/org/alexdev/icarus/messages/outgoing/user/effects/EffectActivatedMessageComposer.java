package org.alexdev.icarus.messages.outgoing.user.effects;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class EffectActivatedMessageComposer extends MessageComposer {

    private Effect effect;

    public EffectActivatedMessageComposer(Effect effect) {
        this.effect = effect;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.EffectActivatedMessageComposer);
        this.response.writeInt(this.effect.getEffectId());
        this.response.writeInt(this.effect.getTimeLeft().get());
        this.response.writeBool(false);
    }
}
