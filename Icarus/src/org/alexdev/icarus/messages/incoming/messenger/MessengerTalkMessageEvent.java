package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public class MessengerTalkMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {
		
		int friendId = request.readInt();
		
		if (!player.getMessenger().isFriend(friendId)) {
			return;
		}
		
		MessengerUser friend = player.getMessenger().getFriend(friendId);
		
		if (friend.isOnline()) {
		
			String message = request.readString();
            friend.getPlayer().send(new MessengerMessageComposer(player.getDetails().getId(), message));
			
		} else {
			// TODO: Offline messaging
		}
	}

}
