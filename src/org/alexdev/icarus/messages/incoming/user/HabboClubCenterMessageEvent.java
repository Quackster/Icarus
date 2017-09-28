package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.user.HabboClubCenterComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class HabboClubCenterMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        player.send(new HabboClubCenterComposer());
    }
}
