package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.encryption.DiffieHellman;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.handshake.InitCryptoMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class InitCryptoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        DiffieHellman dh = player.getDiffieHellman();

        String prime = Util.getRSA().sign(dh.getPrime().toString());
        String generator = Util.getRSA().sign(dh.getGenerator().toString());
        
        player.send(new InitCryptoMessageComposer(prime, generator));
    }

}
