package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

import com.google.common.collect.Lists;

public class SearchResultSetComposer extends MessageComposer {

    private Player player;
    private NavigatorTab navigatorTab;
    private String searchQuery;
    
    public SearchResultSetComposer(Player player, NavigatorTab navigatorTab, String searchQuery) {
        this.player = player;
        this.navigatorTab = navigatorTab;
        this.searchQuery = searchQuery;
    }
    
    @Override
    public void write() {
    
        response.init(Outgoing.SearchResultSetComposer);
        response.writeString(this.navigatorTab.getTabName());
        response.writeString(this.searchQuery);

        if (this.searchQuery.isEmpty()) {

            List<NavigatorTab> tabs = new ArrayList<NavigatorTab>();
            boolean roomLimit = true;
            
            if (this.navigatorTab.getChildId() != -1) { // child tab, aka client requested for more room data through the navigator button
                tabs.add(this.navigatorTab);
                roomLimit = false;
            } else {
                tabs.addAll(this.navigatorTab.getChildTabs());
            }
                
            response.writeInt(tabs.size());
                
            for (NavigatorTab tab : tabs) {
                
                response.writeString(tab.getTabName());
                response.writeString(tab.getTitle());
                response.writeInt(roomLimit ? (int)tab.getButtonType() : 2); // force no button
                response.writeBool(roomLimit ? tab.isClosed() : false); // force collapsed
                response.writeInt(tab.isThumbnail());
                
                List<Room> rooms = Lists.newArrayList();
                
                if (tab.getRoomPopulator() == null) {
                    response.writeInt(0);
                } else {
                    
                    rooms.addAll(tab.getRoomPopulator().generateListing(roomLimit, player));
                    
                    response.writeInt(rooms.size());
                    
                    for (Room room : rooms) {
                        room.getData().serialise(response, false);
                    }
                    
                    rooms = null;
                }
            }
            
            tabs.clear();

        } else { // TODO: Search
            response.writeInt(0);
            
        }
    }
}
