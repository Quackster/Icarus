package org.alexdev.icarus.messages.incoming.trading;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class StartTradingMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        //int tradeWithId = reader.readInt();

    }
}
