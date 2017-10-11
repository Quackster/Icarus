package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorPromoteCategoriesComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class NavigatorPromoteCategoriesMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        player.send(new NavigatorPromoteCategoriesComposer());
    }

}
