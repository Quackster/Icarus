package org.alexdev.icarus.game.navigator.populator;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.game.navigator.NavigatorRoomPopulator;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.enums.RoomState;
import org.alexdev.icarus.game.room.enums.RoomType;
import org.alexdev.icarus.util.GameSettings;

public class PopularPopulator extends NavigatorRoomPopulator {

    @Override
    public List<Room> generateListing(boolean limit, Player player) {

        List<Room> loadedRooms = RoomManager.getRooms();
        List<Room> activeRooms = loadedRooms.stream().filter(r -> r.getData().getUsersNow() > 0 && r.getData().getRoomType() == RoomType.PRIVATE).collect(Collectors.toList());

        for (int i = 0; i < activeRooms.size(); i++) {

            Room room = activeRooms.get(i);

            if (room.getData().getState() == RoomState.INVISIBLE) {
                if (!room.hasRights(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
                    activeRooms.remove(room);
                }
            }
        }

        // Keep first thirty rooms
        if (limit) {
            if (activeRooms.size() > GameSettings.MAX_ROOMS_POPULAR) {
                activeRooms = activeRooms.subList(0, GameSettings.MAX_ROOMS_POPULAR);
            }
        }

        activeRooms.sort((room1, room2)
                ->room2.getData().getUsersNow()
                - room1.getData().getUsersNow());

        return activeRooms;
    }
}
