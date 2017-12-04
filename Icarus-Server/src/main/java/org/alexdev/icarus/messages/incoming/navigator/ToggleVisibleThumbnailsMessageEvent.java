package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ToggleVisibleThumbnailsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        String action = reader.readString();
        boolean setting = reader.readIntAsBool();

        System.out.println("Show thumbnail > " + action + " - " + setting);
    }
}
