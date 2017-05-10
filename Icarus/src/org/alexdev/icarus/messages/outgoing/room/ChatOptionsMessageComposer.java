package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class ChatOptionsMessageComposer implements OutgoingMessageComposer {

	private Room room;

	public ChatOptionsMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.ChatOptionsMessageComposer);
		response.appendInt32(room.getData().getChatType());
		response.appendInt32(room.getData().getChatBalloon());
		response.appendInt32(room.getData().getChatSpeed());
		response.appendInt32(room.getData().getChatMaxDistance());
		response.appendInt32(room.getData().getChatFloodProtection());
	}
}
