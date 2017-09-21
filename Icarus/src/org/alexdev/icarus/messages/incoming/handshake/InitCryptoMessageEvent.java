package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.encryption.DiffieHellman;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.handshake.InitCryptoMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class InitCryptoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        DiffieHellman dh = player.getDiffieHellman();
        String prime = Util.getRSA().Sign(dh.Prime.toString());
        String generator = Util.getRSA().Sign(dh.Generator.toString());
        
        player.send(new InitCryptoMessageComposer(prime, generator));
    }

}
