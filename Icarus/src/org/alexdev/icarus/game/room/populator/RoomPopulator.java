package org.alexdev.icarus.game.room.populator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;

public abstract class RoomPopulator {

    private NavigatorTab navigatorTab;
    
    public NavigatorTab getNavigatorTab() {
        return navigatorTab;
    }
    
    public void setNavigatorTab(NavigatorTab tab) {
        this.navigatorTab = tab;
    }

    public abstract List<Room> generateListing(boolean limit, Player player);
}
