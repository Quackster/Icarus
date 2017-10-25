package org.alexdev.icarus.messages.incoming.pets;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.pets.PetNameVerifyMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class VerifyPetNameMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        String petName = reader.readString();

        if (petName.length() < 2) {
            player.send(new PetNameVerifyMessageComposer(2, "2"));
            return;
        } else if (petName.length() > 15) {
            player.send(new PetNameVerifyMessageComposer(1, "15"));
            return;
        } else if (!Util.isAlphaNumeric(petName)) {
            player.send(new PetNameVerifyMessageComposer(3, ""));
            return;
        }

        player.send(new PetNameVerifyMessageComposer(0, ""));
    }
}
