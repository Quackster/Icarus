package org.alexdev.icarus.messages.outgoing.user.effects;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class EffectActivatedMessageComposer extends MessageComposer {

    private Effect effect;

    public EffectActivatedMessageComposer(Effect effect) {
        this.effect = effect;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.EffectActivatedMessageComposer);
        response.writeInt(this.effect.getEffectId());
        response.writeInt(this.effect.getTimeLeft().get());
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return Outgoing.EffectActivatedMessageComposer;
    }
}