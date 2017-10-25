package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.catalogue.GiftingSettingsComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GiftingSettingsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        player.send(new GiftingSettingsComposer());
    }
}
