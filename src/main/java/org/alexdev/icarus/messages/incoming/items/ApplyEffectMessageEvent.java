package org.alexdev.icarus.messages.incoming.items;

import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.user.effects.EffectActivatedMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

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

        Effect activatedEffect = player.getInventory().getEffectManager().getActivatedEffect();

        if (activatedEffect != null) {
            player.send(new EffectActivatedMessageComposer(activatedEffect));
        }

        effect.setActivated(true);

        player.getRoomUser().applyEffect(effectId);
        player.send(new EffectActivatedMessageComposer(effect));

        //player.send(player.getInventory().getEffectManager().createEffectComposer(effect));
    }
}
