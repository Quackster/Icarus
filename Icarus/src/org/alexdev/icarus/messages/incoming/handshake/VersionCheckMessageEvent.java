package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.messages.AbstractReader;

public class VersionCheckMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {
		
		String version = request.readString();
		
		if (!version.equals("PRODUCTION-201512012203-525044429")) {
			player.getNetwork().close(); // bad version, kill connection
			return;
		}
	}

}
