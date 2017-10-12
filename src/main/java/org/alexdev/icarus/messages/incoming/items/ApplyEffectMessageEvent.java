package org.alexdev.icarus.messages.incoming.items;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ApplyEffectMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int effectId = reader.readInt();
        
        if (effectId < 0) {
            effectId = 0;
        }
        
        player.getRoomUser().applyEffect(effectId);
    }
}
