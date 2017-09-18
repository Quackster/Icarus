package org.alexdev.icarus.game.pets;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.pets.PetDao;

public class PetManager {

    private static Map<Integer, List<PetRace>> races;

    /**
     * Load.
     */
    public static void load() {
        races = PetDao.getPetRaces();
    }

    /**
     * Gets the races.
     *
     * @param raceId the race id
     * @return the races
     */
    public static List<PetRace> getRaces(int raceId) {
        return races.get(raceId);
    }
}
