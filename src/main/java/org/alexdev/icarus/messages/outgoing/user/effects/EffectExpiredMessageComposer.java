package org.alexdev.icarus.messages.outgoing.user.effects;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class EffectExpiredMessageComposer extends MessageComposer {

    private Effect effect;

    public EffectExpiredMessageComposer(Effect effect) {
        this.effect = effect;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.effect.getEffectId());
    }

    @Override
    public short getHeader() {
        return Outgoing.EffectExpiredMessageComposer;
    }
}
