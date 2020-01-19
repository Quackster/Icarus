package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.player.Player;

import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class VersionCheckMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        String version = request.readString();
        
        if (!version.equals("PRODUCTION-201611291003-338511768")) {
            player.getLogger().info("Player kicked, got version instead: " + version);
            player.getNetwork().close(); // bad version, kill connection
        }
    }
}
