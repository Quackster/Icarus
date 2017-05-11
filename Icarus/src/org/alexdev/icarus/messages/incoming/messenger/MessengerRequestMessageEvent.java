package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.dao.mysql.MessengerDao;
import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerSendRequest;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerRequestMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {

		String username = request.readString();

		if (username == null) {
			return;
		}

		int userId = PlayerDao.getId(username);

		if (userId < 1) {
			return;
		}

		if (player.getMessenger().hasReqest(userId)) {
			return;
		}

		//TODO: Check if they have blocked friend requests

		if (MessengerDao.newRequest(player.getDetails().getId(), userId)) {

			MessengerUser user = new MessengerUser(userId);
			player.getMessenger().getRequests().add(user);

			if (user.isOnline()) {
				user.getPlayer().send(new MessengerSendRequest(player.getDetails().getId(), player.getDetails().getUsername(), player.getDetails().getFigure()));
			}
		}
	}
}
