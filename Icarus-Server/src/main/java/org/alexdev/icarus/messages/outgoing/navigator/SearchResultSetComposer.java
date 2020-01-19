package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.game.navigator.preference.NavigatorPreferenceType;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

import java.util.ArrayList;
import java.util.List;

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
    public void compose(Response response) {

        try {
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

                    boolean isClosed = (!this.player.getNavigatorPreference().getFlag(NavigatorPreferenceType.EXPANDED, tab.getTabName()));
                    boolean showThumbnail = this.player.getNavigatorPreference().getFlag(NavigatorPreferenceType.THUMBNAIL, tab.getTabName());

                    response.writeInt(roomLimit ? (int) tab.getButtonType() : 2); // force no button
                    response.writeBool(roomLimit && isClosed); // force collapsed
                    response.writeInt(showThumbnail);

                    List<Room> rooms = new ArrayList<>();

                    if (tab.getRoomPopulator() == null) {
                        response.writeInt(0);
                    } else {

                        rooms.addAll(tab.getRoomPopulator().generateListing(roomLimit, player));

                        response.writeInt(rooms.size());

                        for (Room room : rooms) {
                            RoomUtil.serialise(room, response);
                        }
                    }
                }

                tabs.clear();

            } else { // TODO: Search
                response.writeInt(0);

            }
        } catch (Exception e) {
            Log.getErrorLogger().error("Failure to list search result set due to: ", e);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.SearchResultSetComposer;
    }
}