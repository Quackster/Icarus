package org.alexdev.icarus.game.messenger;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.server.messages.AbstractResponse;
import org.alexdev.icarus.game.player.Player;

public class MessengerUser {

	private int userId;
	private PlayerDetails details;
	private Player player;
	
	public MessengerUser(int userId) {
		this.userId = userId;

		if (this.isOnline()) {
			this.details = this.player.getDetails();
		} else {
			this.details = PlayerDao.getDetails(this.userId);
		}
	}

	public void update() {
		this.player = PlayerManager.findById(this.userId);
	}

	public void serialise(AbstractResponse response, boolean forceOffline) {

		response.appendInt32(this.getDetails().getId());
		response.appendString(this.getDetails().getUsername());
		response.appendInt32(forceOffline ? false : this.isOnline()); // gender
		response.appendBoolean(forceOffline ? false : this.isOnline());
		response.appendBoolean(forceOffline ? false : this.inRoom());

		if (forceOffline) {
			response.appendString("");
			response.appendInt32(0);
			response.appendString("");  
		} else {
			response.appendString(this.isOnline() ? this.getDetails().getFigure() : "");
			response.appendInt32(0);
			response.appendString(this.isOnline() ? this.getDetails().getMotto() : "");  
		}

		response.appendString("");
		response.appendString("");
		response.appendBoolean(true);
		response.appendBoolean(false);
		response.appendBoolean(false);
		response.appendShort(0); 
	}

	public void searchSerialise(AbstractResponse response) {

		response.appendInt32(this.getDetails().getId());
		response.appendString(this.getDetails().getUsername());
		response.appendString(this.getDetails().getMotto()); 
		response.appendBoolean(this.isOnline());
		response.appendBoolean(this.inRoom());
		response.appendString("");
		response.appendInt32(0);
		response.appendString(this.isOnline() ? this.getDetails().getFigure() : ""); 
		response.appendString("");
	}

	public void dispose() {
		this.player = null;
		this.details = null;
	}

	public Player getPlayer() {
		return player;
	}

	public PlayerDetails getDetails() {
		return details;
	}

	public int getUserId() {
		return userId;
	}

	public boolean isOnline() {
		this.update();
		return player != null;
	}

	public boolean inRoom() {
		return isOnline() ? player.getRoomUser().inRoom() : false;
	}

}
