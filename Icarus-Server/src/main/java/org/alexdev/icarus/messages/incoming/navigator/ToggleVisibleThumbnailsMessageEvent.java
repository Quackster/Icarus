package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorPreferenceDao;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.navigator.NavigatorPreference;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ToggleVisibleThumbnailsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        String action = reader.readString();
        boolean setting = reader.readIntAsBool();

        NavigatorTab tab = NavigatorManager.getInstance().getTab(action);
        NavigatorPreferenceDao.update(player.getEntityId(), tab.getId(),  NavigatorPreference.THUMBNAIL, setting);
    }
}
