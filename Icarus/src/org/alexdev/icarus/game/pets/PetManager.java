package org.alexdev.icarus.game.pets;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.pets.PetDao;

public class PetManager {

    private static Map<Integer, List<PetRace>> races;

    public static void load() {
        races = PetDao.getPetRaces();
    }

    public static List<PetRace> getRaces(int raceID) {
        return races.get(raceID);
    }
}
