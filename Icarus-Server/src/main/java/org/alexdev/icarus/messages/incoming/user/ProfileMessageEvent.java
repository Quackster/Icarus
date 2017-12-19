package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.dao.mysql.messenger.player.MessengerDao;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.outgoing.user.LoadProfileMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ProfileMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        PlayerDetails details = PlayerManager.getInstance().getPlayerData(reader.readInt());

        if (details == null) {
            return;
        }

        player.send(new LoadProfileMessageComposer(
                details,
                GroupDao.getMemberGroups(details.getId()),
                player.getMessenger().isFriend(details.getId()),
                player.getMessenger().hasRequest(details.getId()),
                MessengerDao.getFriends(details.getId()).size()));
    }
}
