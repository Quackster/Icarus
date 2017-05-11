package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MessengerCategoriesMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(Response response) {
		
		response.init(Outgoing.MessengerCategoriesMessageComposer);
		response.writeInt(300);//GameSettings.MAX_FRIENDS_DEFAULT); // get max friends
		response.writeInt(400);//GameSettings.MAX_FRIENDS_BASIC);
		response.writeInt(500);//GameSettings.MAX_FRIENDS_VIP);
		response.writeInt(0);
		
	}
}
