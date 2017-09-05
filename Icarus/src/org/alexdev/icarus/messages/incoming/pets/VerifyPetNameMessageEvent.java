package org.alexdev.icarus.messages.incoming.pets;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.pets.VerifyPetNameComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class VerifyPetNameMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        String petName = reader.readString();

        if (petName.length() < 2) {
            player.send(new VerifyPetNameComposer(2, "2"));
            return;
        } else if (petName.length() > 15) {
            player.send(new VerifyPetNameComposer(1, "15"));
            return;
        } else if (!Util.isAlphaNumeric(petName)) {
            player.send(new VerifyPetNameComposer(3, ""));
            return;
        }

        player.send(new VerifyPetNameComposer(0, ""));
    }

}
