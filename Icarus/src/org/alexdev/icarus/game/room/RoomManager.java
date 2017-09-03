package org.alexdev.icarus.game.room;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class RoomManager {

    private static Map<Integer, Room> rooms;
    private static Map<Integer, Room> promotedRooms;
    
    private static ScheduledExecutorService scheduler;

    public static void load() {
    	
        rooms = new ConcurrentHashMap<Integer, Room>();
        promotedRooms = new ConcurrentHashMap<Integer, Room>();
        
        scheduler = GameScheduler.createNewScheduler();
        
        RoomDao.getModels();
        RoomDao.getPublicRooms(true);
    }

    public static void add(Room room) {

        if (rooms.containsKey(room.getData().getId())) {
        	return;
        }

        rooms.put(room.getData().getId(), room);
    }
    
    public static void remove(int id) {
		rooms.remove(id);
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

    public static Room find(int roomId) {

        if (rooms.containsKey(roomId)) {
        	return rooms.get(roomId);
        }

        return null;
    }

    public static List<Room> getRooms() {
        return rooms.values().stream().filter(room -> room != null).collect(Collectors.toList());
    }
    
    public static Map<Integer, Room> getPromotedRooms() {
		return promotedRooms;
	}

	public static ScheduledExecutorService getScheduler() {
        return scheduler;
    }



}
