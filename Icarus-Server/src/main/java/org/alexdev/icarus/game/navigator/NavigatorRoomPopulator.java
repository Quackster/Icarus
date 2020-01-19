package org.alexdev.icarus.game.navigator;

import java.util.List;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;

public abstract class NavigatorRoomPopulator {
    private NavigatorTab navigatorTab;
    
    /**
     * Gets the navigator tab.
     *
     * @return the navigator tab
     */
    public NavigatorTab getNavigatorTab() {
        return navigatorTab;
    }
    
    /**
     * Sets the navigator tab.
     *
     * @param tab the new navigator tab
     */
    public void setNavigatorTab(NavigatorTab tab) {
        this.navigatorTab = tab;
    }

    /**
     * Generate listing.
     *
     * @param limit the limit
     * @param player the player
     * @return the list
     */
    public abstract List<Room> generateListing(boolean limit, Player player);
}
