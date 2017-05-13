package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class MessengerSearchMessageComposer extends OutgoingMessageComposer {

	private List<MessengerUser> friends;
	private List<MessengerUser> strangers;

	public MessengerSearchMessageComposer(List<MessengerUser> friends, List<MessengerUser> strangers) {
		this.friends = friends;
		this.strangers = strangers;
	}

	@Override
	public void write() {

		response.init(Outgoing.MessengerSearchMessageComposer);
		
		response.writeInt(this.friends.size());
		for (MessengerUser friend : this.friends) {
			friend.serialiseSearch(response);
		}

		response.writeInt(this.strangers.size());
		for (MessengerUser stranger : this.strangers) {
			stranger.serialiseSearch(response);
			stranger.dispose();
		}
	}

}
