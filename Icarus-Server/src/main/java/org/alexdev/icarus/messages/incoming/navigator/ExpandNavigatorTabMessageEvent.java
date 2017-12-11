package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorPreferenceDao;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.navigator.NavigatorPreference;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ExpandNavigatorTabMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        NavigatorTab tab = NavigatorManager.getInstance().getTab(reader.readString());

        boolean value = NavigatorPreferenceDao.get(player.getEntityId(), tab.getId(), NavigatorPreference.EXPANDED);
        boolean newValue = !value;

        NavigatorPreferenceDao.update(player.getEntityId(), tab.getId(),  NavigatorPreference.EXPANDED, newValue);
    }
}
