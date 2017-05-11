package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.dao.mysql.MessengerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public class MessengerAcceptMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {
		
		int amount = request.readInt();
	
		for (int i = 0; i < amount; i++) {
			
			int toId = player.getDetails().getId();
			int fromId = request.readInt();
			
			MessengerDao.removeRequest(fromId, toId);
			MessengerDao.newFriend(toId, fromId);
			
			MessengerUser user = new MessengerUser(fromId);
			player.getMessenger().getFriends().add(user);
			
			if (user.isOnline()) {
				
				MessengerUser to = new MessengerUser(toId);
				
				user.getPlayer().getMessenger().getFriends().add(to);
				user.getPlayer().send(new MessengerUpdateMessageComposer(to, false));
			}
			
			player.send(new MessengerUpdateMessageComposer(user, false));
		}

	}

}
