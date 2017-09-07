package org.alexdev.icarus.game.navigator.populator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorRoomPopulator;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import com.google.common.collect.Lists;

public class FriendsPopulator extends NavigatorRoomPopulator {

    @Override
    public List<Room> generateListing(boolean limit, Player player) {
        return Lists.newArrayList();
    }
}
