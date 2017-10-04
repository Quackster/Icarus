package org.alexdev.icarus.messages.outgoing.user.effects;

import java.util.List;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class EffectsMessageComposer extends MessageComposer {

    private List<Effect> effects;

    public EffectsMessageComposer(List<Effect> effects) {
        this.effects = effects;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.EffectsMessageComposer);
        this.response.writeInt(this.effects.size());
        
        for (Effect effect : this.effects) {
            this.response.writeInt(effect.getSpriteId());
            this.response.writeInt(0);
            this.response.writeInt(effect.getDuration().get());
            this.response.writeInt(effect.isActivated() ? effect.getQuantity().get() - 1 : effect.getQuantity().get());
            this.response.writeInt(effect.isActivated() ? effect.getTimeLeft().get() : -1);
            this.response.writeBool(false);
        }
    }

}
