package org.alexdev.icarus.game.messenger;

import java.util.List;
import java.util.Optional;

import org.alexdev.icarus.dao.mysql.MessengerDao;
import org.alexdev.icarus.game.player.Player;

public class Messenger {

	private boolean initalised;
	private Player player;

	private List<MessengerUser> friends;
	private List<MessengerUser> requests;

	public Messenger(Player player) {
		this.player = player;
		this.initalised = false;
	}

	public void load() {
		this.friends = MessengerDao.getFriends(player.getDetails().getId());
		this.requests = MessengerDao.getRequests(player.getDetails().getId());
	}

	public boolean hasReqest(int id) {
		return this.getRequest(id) != null;
	}
	
	public boolean isFriend(int id) {
		return this.getFriend(id) != null;
	}
	
	public MessengerUser getFriend(int id) {
		
		Optional<MessengerUser> friend = this.friends.stream().filter(f -> f.getDetails().getId() == id).findFirst();

		if (friend.isPresent()) {
			return friend.get();
		} else {
			return null;
		}
	}
	
	public MessengerUser getRequest(int id) {

		Optional<MessengerUser> request = this.requests.stream().filter(f -> f.getDetails().getId() == id).findFirst();

		if (request.isPresent()) {
			return request.get();
		} else {
			return null;
		}
	}


	public void removeFriend(int id) {
		MessengerUser user = this.getFriend(id);
		this.friends.remove(user);
	}
	
	public void sendStatus(boolean forceOffline) {

		//AbstractResponse message = new MessengerUpdateMessageComposer(new MessengerUser(this.player.getDetails().getId()), forceOffline);

		for (MessengerUser friend : this.friends) {

			if (friend.isOnline()) {
				if (friend.getPlayer().getMessenger().hasInitalised()) {
					//friend.getPlayer().send(message);
				}
			}
		}
	}
	
	public void dispose() {

		this.sendStatus(false);
		
		if (this.friends != null) {
			this.friends.clear();
			this.friends = null;
		}

		if (this.requests != null) {
			this.requests.clear();
			this.requests = null;
		}

		this.player = null;
	}

	public List<MessengerUser> getFriends() {
		return friends;
	}

	public List<MessengerUser> getRequests() {
		return requests;
	}

	public boolean hasInitalised() {
		return initalised;
	}

	public void setInitalised(boolean initalised) {
		this.initalised = initalised;
	}
}
