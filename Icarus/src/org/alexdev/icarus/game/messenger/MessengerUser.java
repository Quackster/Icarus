package org.alexdev.icarus.game.messenger;

import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.server.api.messages.AbstractResponse;
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

		response.writeInt(this.getDetails().getId());
		response.writeString(this.getDetails().getUsername());
		response.appendInt32(forceOffline ? false : this.isOnline()); // gender
		response.writeBool(forceOffline ? false : this.isOnline());
		response.writeBool(forceOffline ? false : this.inRoom());

		if (forceOffline) {
			response.writeString("");
			response.writeInt(0);
			response.writeString("");  
		} else {
			response.writeString(this.isOnline() ? this.getDetails().getFigure() : "");
			response.writeInt(0);
			response.writeString(this.isOnline() ? this.getDetails().getMotto() : "");  
		}

		response.writeString("");
		response.writeString("");
		response.writeBool(true);
		response.writeBool(false);
		response.writeBool(false);
		response.appendShort(0); 
	}

	public void searchSerialise(AbstractResponse response) {

		response.writeInt(this.getDetails().getId());
		response.writeString(this.getDetails().getUsername());
		response.writeString(this.getDetails().getMotto()); 
		response.writeBool(this.isOnline());
		response.writeBool(this.inRoom());
		response.writeString("");
		response.writeInt(0);
		response.writeString(this.isOnline() ? this.getDetails().getFigure() : ""); 
		response.writeString("");
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
