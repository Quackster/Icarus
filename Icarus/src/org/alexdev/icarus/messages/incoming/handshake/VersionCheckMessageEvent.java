package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class VersionCheckMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        String version = request.readString();
        
        if (!version.equals("PRODUCTION-201709192204-203982672")) {
            player.getNetwork().close(); // bad version, kill connection
            return;
        }
    }

}
