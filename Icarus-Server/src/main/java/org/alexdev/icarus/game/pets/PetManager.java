package org.alexdev.icarus.game.pets;

import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PetManager {

    private Map<Integer, List<PetRace>> races;

    private static final Logger log = LoggerFactory.getLogger(PetManager.class);
    private static PetManager instance;


    public PetManager() {
        this.races = PetDao.getPetRaces();

        if (Configuration.getInstance().getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} pet races", this.races.size());
        }
    }

    /**
     * Gets the races.
     *
     * @param raceId the race id
     * @return the races
     */
    public List<PetRace> getRaces(int raceId) {
        return races.get(raceId);
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static PetManager getInstance() {

        if (instance == null) {
            instance = new PetManager();
        }

        return instance;
    }
}
