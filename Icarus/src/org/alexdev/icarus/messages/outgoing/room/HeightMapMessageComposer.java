package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class HeightMapMessageComposer implements OutgoingMessageComposer {

	private Room room;
	private int mapSizeX;
	private int mapSizeY;

	public HeightMapMessageComposer(Room room, int mapSizeX, int mapSizeY) {
		this.room = room;
		this.mapSizeX = mapSizeX;
		this.mapSizeY = mapSizeY;
	}

	@Override
	public void write(Response response) {
		RoomModel roomModel = room.getData().getModel();

		response.init(Outgoing.HeightMapMessageComposer);
		response.writeInt(this.mapSizeX);
		response.writeInt(this.mapSizeX * mapSizeY);
		
		for (int y = 0; y < this.mapSizeY; y++) {
			for (int x = 0; x < this.mapSizeX; x++) {

				//if (room.getCollisionMap()[x][y] == RoomModel.CLOSED) {
				//	this.appendShort (-1);
				//} else {
				response.writeShort((int) (roomModel.getHeight(x, y) * 256));
				//}
			}
		}
	}

}
