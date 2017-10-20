package org.alexdev.icarus.messages.incoming.camera;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.camera.PhotoPriceMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PhotoPricingMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        player.send(new PhotoPriceMessageComposer(50, 50));
    }
}
