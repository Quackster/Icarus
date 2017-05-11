package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.user.CreditsMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CurrencyBalanceMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage reader) {

		/*Response response = new Response();
		response.init(Outgoing.ActivityPointsMessageComposer);
		response.appendInt32(2);
		response.appendInt32(0);
		response.appendInt32(1337);
		response.appendInt32(5);
		response.appendInt32(44444);
		player.send(response);*/
		
		player.send(new CreditsMessageComposer(player.getDetails().getCredits()));		
	}

}
