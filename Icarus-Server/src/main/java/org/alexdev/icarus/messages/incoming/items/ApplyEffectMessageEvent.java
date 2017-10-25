package org.alexdev.icarus.messages.incoming.items;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.user.effects.EffectActivatedMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.effects.EffectExpiredMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.effects.EffectsMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import java.util.List;

public class ApplyEffectMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int effectId = reader.readInt();

        Effect effect = player.getInventory().getEffectManager().getEffect(effectId);

        if (effect == null) {
            return;
        }
        
        if (effectId < 0) {
            effectId = 0;
        }

        player.getInventory().getEffectManager().deactivateEffects();
        player.getRoomUser().applyEffect(effectId);

        effect.setActivated(true);

        player.send(new EffectExpiredMessageComposer(effect)); // Remove effect
        player.send(new EffectsMessageComposer(effect)); // Re-add the effect

        player.send(new EffectActivatedMessageComposer(effect)); // Activate the effect
    }
}
