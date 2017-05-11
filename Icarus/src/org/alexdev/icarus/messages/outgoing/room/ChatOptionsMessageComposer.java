package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class ChatOptionsMessageComposer implements OutgoingMessageComposer {

	private Room room;

	public ChatOptionsMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.ChatOptionsMessageComposer);
		response.writeInt(room.getData().getChatType());
		response.writeInt(room.getData().getChatBalloon());
		response.writeInt(room.getData().getChatSpeed());
		response.writeInt(room.getData().getChatMaxDistance());
		response.writeInt(room.getData().getChatFloodProtection());
	}
}
