package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RoomDataMessageComposer implements OutgoingMessageComposer {

	private Room room;
	private Player player;
	private boolean isLoading;
	private boolean stalkingRoom;

	public RoomDataMessageComposer(Room room, Player player, boolean isLoading, boolean stalkingRoom) {
		this.room = room;
		this.player = player;
		this.isLoading = isLoading;
		this.stalkingRoom = stalkingRoom;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.RoomDataMessageComposer);
		response.writeBool(this.isLoading);
		this.room.getData().serialise(response, this.isLoading);
		response.writeBool(this.stalkingRoom);
		response.writeBool(false); 
		response.writeBool(false);
		response.writeBool(false);
		response.appendInt32(false);
		response.appendInt32(false);
		response.appendInt32(false);
		response.writeBool(this.room.hasRights(this.player, true));
		response.writeInt(0);
		response.writeInt(0);
		response.writeInt(0);
		response.writeInt(0);
		response.writeInt(0);
	}
}
