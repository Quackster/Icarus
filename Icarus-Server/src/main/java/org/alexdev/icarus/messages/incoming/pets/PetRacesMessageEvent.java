package org.alexdev.icarus.messages.incoming.pets;

import java.util.List;

import org.alexdev.icarus.game.pets.PetManager;
import org.alexdev.icarus.game.pets.PetRace;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.pets.PetRacesMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class PetRacesMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        String petRace = reader.readString();
        String petRaceId = petRace.replace("a0 pet", "");
        
        if (!Util.isNumber(petRaceId) || !(petRaceId.length() > 0)) {
            return;
        }
        
        int raceId = Integer.valueOf(petRaceId);
        List<PetRace> races = PetManager.getInstance().getRaces(raceId);
        
        if (races == null) {
            return;
        }
        
        player.send(new PetRacesMessageComposer(petRace, races));
    }
}
