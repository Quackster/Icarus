package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorPreferenceDao;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.navigator.preference.NavigatorPreference;
import org.alexdev.icarus.game.navigator.preference.NavigatorPreferenceType;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CloseNavigatorTabMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        NavigatorTab tab = NavigatorManager.getInstance().getTab(reader.readString());

        boolean value = NavigatorPreferenceDao.get(player.getEntityId(), tab.getId(), NavigatorPreferenceType.EXPANDED);
        NavigatorPreferenceDao.update(player.getEntityId(), tab.getId(), NavigatorPreferenceType.EXPANDED, !value);

        player.getNavigatorPreference().addPreference(NavigatorPreferenceType.EXPANDED, tab.getTabName(), !value);
    }
}
