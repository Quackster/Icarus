package org.alexdev.icarus.factories;

import org.alexdev.icarus.game.messenger.MessengerUser;

public class MessengerFactory {

	public static MessengerUser getUser(int userId) {
		MessengerUser user = new MessengerUser(userId);
		return user;
	}

}
