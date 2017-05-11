package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.user.SendPerkAllowancesMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.UserObjectMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public class InfoRetrieveMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {
		player.send(new UserObjectMessageComposer(player.getDetails()));
		player.send(new SendPerkAllowancesMessageComposer());
	}

}
