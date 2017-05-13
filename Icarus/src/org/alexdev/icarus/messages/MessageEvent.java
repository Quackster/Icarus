package org.alexdev.icarus.messages;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public interface MessageEvent {
    public void handle(Player player, ClientMessage reader);
}
