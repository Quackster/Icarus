package org.alexdev.icarus.messages.outgoing.user.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

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
    public void write() {
        this.response.init(Outgoing.EffectsMessageComposer);
        this.response.writeInt(this.effects.size());
        
        for (Effect effect : this.effects) {
            this.response.writeInt(effect.getEffectId());
            this.response.writeInt(0);
            this.response.writeInt(effect.getTimeLeft().get());
            this.response.writeInt(effect.isActivated() ? effect.getQuantity().get() - 1 : effect.getQuantity().get());
            this.response.writeInt(effect.isActivated() ? effect.getTimeLeft().get() : -1);
            this.response.writeBool(false);
        }
    }

}
