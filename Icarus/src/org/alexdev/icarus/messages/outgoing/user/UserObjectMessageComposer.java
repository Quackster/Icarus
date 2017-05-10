package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class UserObjectMessageComposer implements OutgoingMessageComposer {

	private PlayerDetails details;

	public UserObjectMessageComposer(PlayerDetails details) {
		this.details = details;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.UserObjectMessageComposer);
		response.appendInt32(this.details.getId()); // User ID
		response.appendString(this.details.getUsername()); // Username
		response.appendString(this.details.getFigure()); // Figure
		response.appendString("M"); // Gender
		response.appendString(this.details.getMotto()); // Motto
		response.appendString(""); // ?
		response.appendBoolean(false); // ?
		response.appendInt32(0); // Respect
		response.appendInt32(3); // Daily Respect Points
		response.appendInt32(3); // Daily Pet Respect Points
		response.appendBoolean(true); // ?
		response.appendString("1448526834"); // Last Online (format?)
		response.appendBoolean(true); // Can Change Username
		response.appendBoolean(false); // ?
	}

}
