package org.alexdev.icarus.game.room;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.room.enums.RoomType;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

public class RoomManager {

    private Map<Integer, Room> rooms;
    private Map<Integer, Room> promotedRooms;
    private HashMap<String, RoomModel> roomModels;

    private static ScheduledExecutorService scheduler;
    private static RoomManager instance;

    private final Logger log = LoggerFactory.getLogger(RoomManager.class);

    public RoomManager() {
        this.rooms = new ConcurrentHashMap<>();
        this.promotedRooms = new ConcurrentHashMap<>();
        this.roomModels = RoomModelDao.getModels();
        this.addRooms(RoomDao.getPublicRooms());

        if (Configuration.getInstance().getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} room models ", this.roomModels.size());
            log.info("Loaded {} public rooms", this.rooms.size());
        }
    }

    /**
     * Adds the room.
     *
     * @param room the room
     */
    public void addRoom(Room room) {
        if (this.rooms.containsKey(room.getData().getId())) {
            return;
        }

        this.rooms.put(room.getData().getId(), room);
    }

    /**
     * Adds list of rooms
     *
     * @param rooms the rooms
     */
    public void addRooms(Collection<Room> rooms) {

        for (Room room : rooms) {
            this.addRoom(room);
        }
    }


    /**
     * Adds the promoted room.
     *
     * @param id the id
     * @param room the room
     */
    public void addPromotedRoom(int id, Room room) {
        if (this.promotedRooms.containsKey(room.getData().getId())) {
            return;
        }

        this.promotedRooms.put(room.getData().getId(), room);
    }
    
    /**
     * Removes the room.
     *
     * @param id the id
     */
    public void removeRoom(int id) {
        if (this.rooms.containsKey(id)) {
            this.rooms.remove(Integer.valueOf(id));
        }
        
        if (this.promotedRooms.containsKey(id)) {
            removePromotedRoom(id);
        }
    }
    
    /**
     * Removes the promoted room.
     *
     * @param id the id
     */
    public void removePromotedRoom(int id) {
        this.promotedRooms.remove(Integer.valueOf(id));
    }
        
    /**
     * Gets the public rooms.
     *
     * @return the public rooms
     */
    public List<Room> getPublicRooms() {
        return this.rooms.values().stream().filter(room -> room.getData().getRoomType() == RoomType.PUBLIC).collect(Collectors.toList());
    }
    
    /**
     * Gets the player rooms.
     *
     * @param userId the user id
     * @return the player rooms
     */
    public List<Room> getPlayerRooms(int userId) {
        return this.rooms.values().stream().filter(room -> room.getData().getOwnerId() == userId).collect(Collectors.toList());
    }

    /**
     * Gets the by room id.
     *
     * @param roomId the room id
     * @return the by room id
     */
    public Room getByRoomId(int roomId) {
        if (this.rooms.containsKey(roomId)) {
            return this.rooms.get(roomId);
        }

        return null;
    }
 
    /**
     * Gets the rooms.
     *
     * @return the rooms
     */
    public List<Room> getRooms() {
        return this.rooms.values().stream().filter(room -> room != null).collect(Collectors.toList());
    }
    
    /**
     * Gets the promoted rooms.
     *
     * @return the promoted rooms
     */
    public List<Room> getPromotedRooms() {
        return this.promotedRooms.values().stream().filter(room -> room != null).collect(Collectors.toList());
    }

    /**
     * Gets the model.
     *
     * @param model the model
     * @return the model
     */
    public RoomModel getModel(String model) {
        return roomModels.get(model);
    }

    /**
     * Gets the scheduler.
     *
     * @return the scheduler
     */
    public ScheduledExecutorService getScheduleService() {
        if (scheduler == null) {
            scheduler = GameScheduler.createNewScheduler();
        }

        return scheduler;
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static RoomManager getInstance() {

        if (instance == null) {
            instance = new RoomManager();
        }

        return instance;
    }
}
