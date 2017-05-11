package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UserObjectMessageComposer extends OutgoingMessageComposer {

	private PlayerDetails details;

	public UserObjectMessageComposer(PlayerDetails details) {
		this.details = details;
	}

	@Override
	public void write() {
		response.init(Outgoing.UserObjectMessageComposer);
		response.writeInt(this.details.getId()); // User ID
		response.writeString(this.details.getUsername()); // Username
		response.writeString(this.details.getFigure()); // Figure
		response.writeString("M"); // Gender
		response.writeString(this.details.getMotto()); // Motto
		response.writeString(""); // ?
		response.writeBool(false); // ?
		response.writeInt(0); // Respect
		response.writeInt(3); // Daily Respect Points
		response.writeInt(3); // Daily Pet Respect Points
		response.writeBool(true); // ?
		response.writeString("1448526834"); // Last Online (format?)
		response.writeBool(true); // Can Change Username
		response.writeBool(false); // ?
	}

}
