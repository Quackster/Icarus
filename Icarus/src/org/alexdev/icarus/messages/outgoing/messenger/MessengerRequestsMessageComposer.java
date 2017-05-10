package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class MessengerRequestsMessageComposer implements OutgoingMessageComposer {

	private Player player;
	private List<MessengerUser> requests;

	public MessengerRequestsMessageComposer(Player player, List<MessengerUser> requests) {
		this.player = player;
		this.requests = requests;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.MessengerRequestsMessageComposer);
		response.appendInt32(this.player.getDetails().getId());
		response.appendInt32(this.requests.size()); 

		for (MessengerUser user : this.requests) {
			response.appendInt32(user.getUserId());
			response.appendString(user.getDetails().getUsername());
			response.appendString(user.getDetails().getFigure());
		}
	}

}
