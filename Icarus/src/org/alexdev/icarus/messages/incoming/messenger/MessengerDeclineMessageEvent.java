package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.dao.mysql.MessengerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerDeclineMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {

		boolean deleteAll = request.readBoolean();

		if (!deleteAll) {

			request.readInt();
			int fromId = request.readInt();
			
			MessengerDao.removeRequest(fromId, player.getDetails().getId());
			
		} else {
			
			for (int i = 0; i < player.getMessenger().getRequests().size(); i++) {
				
				MessengerUser user = player.getMessenger().getRequests().get(i);
				MessengerDao.removeRequest(user.getUserId(), player.getDetails().getId());
				player.getMessenger().getRequests().remove(user);
			}
		}
	}
}
