package org.alexdev.icarus.game.navigator.populator;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorRoomPopulator;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;

public class OfficialRoomPopulator extends NavigatorRoomPopulator {
    
    @Override
    public List<Room> generateListing(boolean limit, Player player) {
        
        List<Room> rooms =  RoomManager.getInstance().getPublicRooms();
        
        if (rooms == null) {
            return new ArrayList<Room>();
        }                                
        
        rooms.sort((room1, room2) -> 
        room2.getData().getUsersNow() - 
        room1.getData().getUsersNow());
        
        return rooms;
    }
}
