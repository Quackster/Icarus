package org.alexdev.icarus.game.room;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.room.settings.RoomType;

public class RoomManager {

    private static Map<Integer, Room> rooms;
    private static Map<Integer, Room> promotedRooms;
    
    private static ScheduledExecutorService scheduler;

    public static void load() {
        
        rooms = new ConcurrentHashMap<Integer, Room>();
        promotedRooms = new ConcurrentHashMap<Integer, Room>();
        
        scheduler = GameScheduler.createNewScheduler();
        
        RoomModelDao.getModels();
        RoomDao.getPublicRooms(true);
    }

    public static void addRoom(Room room) {

        if (rooms.containsKey(room.getData().getId())) {
            return;
        }

        rooms.put(room.getData().getId(), room);
    }

    public static void addPromotedRoom(int id, Room room) {

        if (promotedRooms.containsKey(room.getData().getId())) {
            return;
        }

        promotedRooms.put(room.getData().getId(), room);
    }
    
    public static void removeRoom(int id) {
        
        rooms.remove(id);
        
        if (promotedRooms.containsKey(id)) {
            removePromotedRoom(id);
        }
    }
    
    public static void removePromotedRoom(int id) {
        promotedRooms.remove(id);
    }
        
    public static List<Room> getPublicRooms() {
        
        try {
            return rooms.values().stream().filter(room -> room.getData().getRoomType() == RoomType.PUBLIC).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<Room> getPlayerRooms(int userId) {
        
        try {
            return rooms.values().stream().filter(room -> room.getData().getOwnerId() == userId).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public static Room getByRoomId(int roomId) {

        if (rooms.containsKey(roomId)) {
            return rooms.get(roomId);
        }

        return null;
    }
 
    public static List<Room> getRooms() {  
        return rooms.values().stream().filter(room -> room != null).collect(Collectors.toList());
    }
    
    public static List<Room> getPromotedRooms() {
        return promotedRooms.values().stream().filter(room -> room != null).collect(Collectors.toList());
    }

    public static ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
