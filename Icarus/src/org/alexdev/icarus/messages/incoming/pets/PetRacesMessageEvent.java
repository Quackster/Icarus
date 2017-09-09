package org.alexdev.icarus.messages.incoming.pets;

import java.util.List;

import org.alexdev.icarus.game.pets.PetManager;
import org.alexdev.icarus.game.pets.PetRace;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.pets.PetRacesMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class PetRacesMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        String petRace = reader.readString();
        String petRaceID = petRace.replace("a0 pet", "");
        
        if (!Util.isNumber(petRaceID) || !(petRaceID.length() > 0)) {
            return;
        }
        
        int raceID = Integer.valueOf(petRaceID);
        List<PetRace> races = PetManager.getRaces(raceID);
        
        if (races == null) {
            return;
        }
        
        player.send(new PetRacesMessageComposer(petRace, races));
    }
}
