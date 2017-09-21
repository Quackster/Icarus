package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.handshake.SecretKeyMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class GenerateSecretKeyMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        String cipherPublicKey = reader.readString();
        String plaintextKey = Util.getRSA().Decrypt(cipherPublicKey).replace(Character.toString((char) 0), "");
        String publicKey = Util.getRSA().Sign(player.getDiffieHellman().getPublicKey().toString());
        
        player.send(new SecretKeyMessageComposer(publicKey));

        player.getDiffieHellman().GenerateSharedKey(plaintextKey);
        player.setRC4(player.getDiffieHellman().getSharedKey().toByteArray());

    }

}
