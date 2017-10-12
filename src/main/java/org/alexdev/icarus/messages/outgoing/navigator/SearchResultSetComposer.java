package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

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
    
        this.response.init(Outgoing.SearchResultSetComposer);
        this.response.writeString(this.navigatorTab.getTabName());
        this.response.writeString(this.searchQuery);

        if (this.searchQuery.isEmpty()) {

            List<NavigatorTab> tabs = new ArrayList<NavigatorTab>();
            boolean roomLimit = true;
            
            if (this.navigatorTab.getChildId() != -1) { // child tab, aka client requested for more room data through the navigator button
                tabs.add(this.navigatorTab);
                roomLimit = false;
            } else {
                tabs.addAll(this.navigatorTab.getChildTabs());
            }
                
            this.response.writeInt(tabs.size());
                
            for (NavigatorTab tab : tabs) {
                
                this.response.writeString(tab.getTabName());
                this.response.writeString(tab.getTitle());
                this.response.writeInt(roomLimit ? (int)tab.getButtonType() : 2); // force no button
                this.response.writeBool(roomLimit ? tab.isClosed() : false); // force collapsed
                this.response.writeInt(tab.isThumbnail());
                
                List<Room> rooms = new ArrayList<>();
                
                if (tab.getRoomPopulator() == null) {
                    this.response.writeInt(0);
                } else {
                    
                    rooms.addAll(tab.getRoomPopulator().generateListing(roomLimit, player));
                    
                    this.response.writeInt(rooms.size());
                    
                    for (Room room : rooms) {
                        RoomUtil.serialise(room, response, false);
                    }
                    
                    rooms = null;
                }
            }
            
            tabs.clear();

        } else { // TODO: Search
            this.response.writeInt(0);
            
        }
    }
}
