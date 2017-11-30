package org.alexdev.icarus.game.navigator.populator;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.navigator.NavigatorRoomPopulator;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomState;

public class FriendsPopulator extends NavigatorRoomPopulator {

    @Override
    public List<Room> generateListing(boolean limit, Player player) {
        List<Room> friendRooms = new ArrayList<>();

        // Get a list of rooms, but do NOT store them in memory
        for (MessengerUser friend : player.getMessenger().getFriends()) {
            List<Room> rooms = RoomDao.getPlayerRooms(friend.getDetails().getId());
            friendRooms.addAll(rooms);
        }

        for (int i = 0; i < friendRooms.size(); i++) {
            Room room = friendRooms.get(i);

            if (room.getData().getState() == RoomState.INVISIBLE) {
                if (!room.hasRights(player.getEntityId())) {
                    friendRooms.remove(room);
                }
            }
        }
                
        // Keep first thirty rooms
        if (limit) {
            if (friendRooms.size() > GameSettings.MAX_ROOMS_SUB_CATEGORY) {
                friendRooms = friendRooms.subList(0, GameSettings.MAX_ROOMS_SUB_CATEGORY);
            }
        }

        friendRooms.sort((room1, room2)
                ->room2.getData().getUsersNow()
                - room1.getData().getUsersNow());

        return friendRooms;
    }
}
