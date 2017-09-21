package org.alexdev.icarus.messages.types;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public interface MessageEvent {
    
    /**
     * Handle the incoming client message.
     *
     * @param player the player
     * @param reader the reader
     */
    public void handle(Player player, ClientMessage reader);
}
