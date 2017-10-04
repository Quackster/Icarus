package org.alexdev.icarus.game.room;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.room.enums.RoomType;

public class RoomManager {

    private static Map<Integer, Room> rooms;
    private static Map<Integer, Room> promotedRooms;
    private static ScheduledExecutorService scheduler;

    /**
     * Load.
     */
    public static void load() {
        
        rooms = new ConcurrentHashMap<>();
        promotedRooms = new ConcurrentHashMap<>();
        scheduler = GameScheduler.createNewScheduler();
        
        RoomModelDao.getModels();
        RoomDao.getPublicRooms(true);
    }

    /**
     * Adds the room.
     *
     * @param room the room
     */
    public static void addRoom(Room room) {

        if (rooms.containsKey(room.getData().getId())) {
            return;
        }

        rooms.put(room.getData().getId(), room);
    }

    /**
     * Adds the promoted room.
     *
     * @param id the id
     * @param room the room
     */
    public static void addPromotedRoom(int id, Room room) {

        if (promotedRooms.containsKey(room.getData().getId())) {
            return;
        }

        promotedRooms.put(room.getData().getId(), room);
    }
    
    /**
     * Removes the room.
     *
     * @param id the id
     */
    public static void removeRoom(int id) {
   
        if (rooms.containsKey(id)) {
            
            Room room = getByRoomId(id);
            room.saveMetadata();
            
            rooms.remove(Integer.valueOf(id));
        }
        
        if (promotedRooms.containsKey(id)) {
            removePromotedRoom(id);
        }
    }
    
    /**
     * Removes the promoted room.
     *
     * @param id the id
     */
    public static void removePromotedRoom(int id) {
        promotedRooms.remove(Integer.valueOf(id));
    }
        
    /**
     * Gets the public rooms.
     *
     * @return the public rooms
     */
    public static List<Room> getPublicRooms() {
        
        try {
            return rooms.values().stream().filter(room -> room.getData().getRoomType() == RoomType.PUBLIC).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Gets the player rooms.
     *
     * @param userId the user id
     * @return the player rooms
     */
    public static List<Room> getPlayerRooms(int userId) {
        
        try {
            return rooms.values().stream().filter(room -> room.getData().getOwnerId() == userId).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets the by room id.
     *
     * @param roomId the room id
     * @return the by room id
     */
    public static Room getByRoomId(int roomId) {

        if (rooms.containsKey(roomId)) {
            return rooms.get(roomId);
        }

        return null;
    }
 
    /**
     * Gets the rooms.
     *
     * @return the rooms
     */
    public static List<Room> getRooms() {  
        return rooms.values().stream().filter(room -> room != null).collect(Collectors.toList());
    }
    
    /**
     * Gets the promoted rooms.
     *
     * @return the promoted rooms
     */
    public static List<Room> getPromotedRooms() {
        return promotedRooms.values().stream().filter(room -> room != null).collect(Collectors.toList());
    }

    /**
     * Gets the scheduler.
     *
     * @return the scheduler
     */
    public static ScheduledExecutorService getScheduledPool() {
        return scheduler;
    }
}
