package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;
import org.alexdev.icarus.util.GameSettings;

public class MessengerCategoriesMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.MessengerCategoriesMessageComposer);
		response.writeInt(GameSettings.MAX_FRIENDS_DEFAULT); // get max friends
		response.writeInt(GameSettings.MAX_FRIENDS_DEFAULT);
		response.writeInt(GameSettings.MAX_FRIENDS_BASIC);
		response.writeInt(GameSettings.MAX_FRIENDS_VIP);
		response.writeInt(0);
		
	}
}
