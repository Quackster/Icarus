package org.alexdev.icarus.game.navigator.populator;

import java.util.List;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.navigator.NavigatorRoomPopulator;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomState;
import org.alexdev.icarus.util.GameSettings;

import com.google.common.collect.Lists;

public class FriendsPopulator extends NavigatorRoomPopulator {

    @Override
    public List<Room> generateListing(boolean limit, Player player) {

        List<Room> friendRooms = Lists.newArrayList();

        for (MessengerUser friend : player.getMessenger().getFriends()) {

            // Get a list of rooms, but do NOT store them in memory
            List<Room> rooms = RoomDao.getPlayerRooms(friend.getDetails().getId(), false);
            friendRooms.addAll(rooms);
        }

        for (int i = 0; i < friendRooms.size(); i++) {

            Room room = friendRooms.get(i);

            if (room.getData().getState() == RoomState.INVISIBLE) {
                if (!room.hasRights(player.getDetails().getId(), false)) {
                    friendRooms.remove(room);
                }
            }
        }
                
        // Keep first thirty rooms
        if (limit) {
            if (friendRooms.size() > GameSettings.MAX_ROOMS_SUB_CATEGORIES) {
                friendRooms = friendRooms.subList(0, GameSettings.MAX_ROOMS_SUB_CATEGORIES);
            }
        }

        friendRooms.sort((room1, room2)
                ->room2.getData().getUsersNow()
                - room1.getData().getUsersNow());

        return friendRooms;
    }
}
