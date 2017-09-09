package org.alexdev.icarus.game.navigator.populator;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorRoomPopulator;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;

public class MyRoomPopulator extends NavigatorRoomPopulator {
    
    @Override
    public List<Room> generateListing(boolean limit, Player player) {
        
        List<Room> rooms = RoomManager.getPlayerRooms(player.getDetails().getID());
        
        if (rooms == null) {
            rooms = new ArrayList<Room>();
        }
        
        rooms.sort((room1, room2) -> 
        room2.getData().getUsersNow() - 
        room1.getData().getUsersNow());
        
        return rooms;
    }
}
