package org.alexdev.icarus.game.pets;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PetManager {

    private static Map<Integer, List<PetRace>> races;
    private static final Logger log = LoggerFactory.getLogger(PetManager.class);
    /**
     * Load.
     */
    public static void load() {
        races = PetDao.getPetRaces();

        if (Util.getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} pet races", races.size());
        }
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
