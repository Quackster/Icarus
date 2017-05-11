package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FriendsListMessageComposer implements OutgoingMessageComposer {

	private List<MessengerUser> friends;

	public FriendsListMessageComposer(List<MessengerUser> friends) {
		this.friends = friends;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.InitMessengerMessageComposer);
		response.writeInt(1);
		response.writeInt(0);
		response.writeInt(this.friends.size());
		
		for (MessengerUser friend : this.friends) {
			friend.serialise(response, false);
		}
	}
}