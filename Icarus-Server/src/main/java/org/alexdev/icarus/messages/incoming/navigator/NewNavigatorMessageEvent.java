package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorPreferenceDao;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.navigator.NavigatorPreference;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.navigator.FlatCategoriesMessageComposer;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorCategoriesMessageComposer;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorPreferencesMessagesComposer;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorTabsMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class NewNavigatorMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        /*
        this.response.init(Outgoing.NavigatorSavedSearchesComposer);
        this.response.appendInt32(1);
        this.response.appendInt32(1);
        this.response.appendString("myworld_view");
        this.response.appendString("test3");
        this.response.appendString("");
        player.send(response);
        */

        for (NavigatorTab tab : NavigatorManager.getInstance().getAllTabs()) {
            if (NavigatorManager.getInstance().getParentTabs().contains(tab)) {
                continue;
            }

            NavigatorPreferenceDao.create(player.getEntityId(), tab.getId(), NavigatorPreference.THUMBNAIL, tab.isThumbnail());
            NavigatorPreferenceDao.create(player.getEntityId(), tab.getId(), NavigatorPreference.EXPANDED, !tab.isClosed());
        }
        
        player.sendQueued(new NavigatorTabsMessageComposer(NavigatorManager.getInstance().getParentTabs()));
        player.sendQueued(new FlatCategoriesMessageComposer(NavigatorManager.getInstance().getCategories()));
        player.sendQueued(new NavigatorCategoriesMessageComposer(NavigatorManager.getInstance().getCategories()));
        player.sendQueued(new NavigatorPreferencesMessagesComposer());
        player.flushQueue();
    }

}
