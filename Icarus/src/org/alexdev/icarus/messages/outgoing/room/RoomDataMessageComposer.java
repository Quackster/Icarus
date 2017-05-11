package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomDataMessageComposer extends OutgoingMessageComposer {

	private Room room;
	private Player player;
	private boolean isLoading;
	private boolean checkEntry;

	public RoomDataMessageComposer(Room room, Player player, boolean isLoading, boolean checkEntry) {
		this.room = room;
		this.player = player;
		this.isLoading = isLoading;
		this.checkEntry = checkEntry;
	}

	@Override
	public void write() {
		response.init(Outgoing.RoomDataMessageComposer);
		response.writeBool(this.isLoading);
		this.room.getData().serialise(response, this.isLoading);
		response.writeBool(this.checkEntry);
		response.writeBool(false); 
		response.writeBool(false);
		response.writeBool(false);
        response.writeInt(room.getData().getWhoCanMute());
        response.writeInt(room.getData().getWhoCanKick());
        response.writeInt(room.getData().getWhoCanBan());
        response.writeBool(room.hasRights(player, true)); // TODO: Rights, true if moderator or room owner
        response.writeInt(room.getData().getChatMode());
        response.writeInt(room.getData().getChatSize());
        response.writeInt(room.getData().getChatSpeed());
        response.writeInt(room.getData().getChatMaxDistance());
        response.writeInt(room.getData().getChatFloodProtection());
        
        /*response.writeInt(room->getData()->chat_speed);
        response.writeInt(room->getData()->chat_flood);
        response.writeInt(room->getData()->chat_distance)*/;
	}
}
