package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.navigator.SearchResultSetComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SearchNewNavigatorEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        String tab = request.readString();
        String searchQuery = request.readString();
        
        NavigatorTab navigatorTab = NavigatorManager.getTab(tab);
        
        if (navigatorTab == null) {
            return;
        }
        
        player.send(new SearchResultSetComposer(player, navigatorTab, searchQuery));
    }
}
