package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class MessengerSearchMessageComposer implements OutgoingMessageComposer {

	private List<MessengerUser> friends;
	private List<MessengerUser> strangers;

	public MessengerSearchMessageComposer(List<MessengerUser> friends, List<MessengerUser> strangers) {
		this.friends = friends;
		this.strangers = strangers;
	}

	@Override
	public void write(AbstractResponse response) {

		response.init(Outgoing.MessengerSearchMessageComposer);
		
		response.appendInt32(this.friends.size());
		for (MessengerUser friend : this.friends) {
			friend.searchSerialise(response);
		}

		response.appendInt32(this.strangers.size());
		for (MessengerUser stranger : this.strangers) {
			stranger.searchSerialise(response);
			stranger.dispose();
		}
	}

}
