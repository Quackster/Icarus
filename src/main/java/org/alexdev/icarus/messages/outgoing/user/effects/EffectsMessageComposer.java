package org.alexdev.icarus.messages.outgoing.user.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class EffectsMessageComposer extends MessageComposer {

    private Collection<Effect> effects;

    public EffectsMessageComposer(Collection<Effect> effects) {
        this.effects = effects;
    }

    public EffectsMessageComposer(Effect effect) {
        this.effects = new ArrayList<Effect>();
        this.effects.add(effect);
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.EffectsMessageComposer);
        response.writeInt(this.effects.size());

        for (Effect effect : this.effects) {
            response.writeInt(effect.getEffectId());
            response.writeInt(0);
            response.writeInt(effect.getTimeLeft().get());
            response.writeInt(effect.isActivated() ? effect.getQuantity().get() - 1 : effect.getQuantity().get());
            response.writeInt(effect.isActivated() ? effect.getTimeLeft().get() : -1);
            response.writeBool(false);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.EffectsMessageComposer;
    }
}
