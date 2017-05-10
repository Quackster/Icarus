package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RoomSettingsDataMessageComposer implements OutgoingMessageComposer {

	private Room room;

	public RoomSettingsDataMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write(AbstractResponse response) {
		
		RoomData data = this.room.getData();
		
		response.init(Outgoing.RoomEditSettingsComposer);
        response.appendInt32(data.getId());
        response.appendString(data.getName());
        response.appendString(data.getDescription());
        response.appendInt32(data.getState().getStateCode());
        response.appendInt32(data.getCategory());
        response.appendInt32(data.getUsersMax());
        response.appendInt32(data.getUsersMax());
        response.appendInt32(0);
        /*foreach (var s in room.RoomData.Tags)
        {
            response.appendString(s);
        }*/
        response.appendInt32(data.getTradeState());
        response.appendInt32(data.isAllowPets());
        response.appendInt32(data.isAllowPetsEat());
        response.appendInt32(data.isAllowWalkthrough());
        response.appendInt32(data.isHideWall());
        response.appendInt32(data.getWallThickness());
        response.appendInt32(data.getFloorThickness());
        response.appendInt32(data.getChatType());//room.RoomData.ChatType);
        response.appendInt32(data.getChatBalloon());//room.RoomData.ChatBalloon);
        response.appendInt32(data.getChatSpeed());//room.RoomData.ChatSpeed);
        response.appendInt32(data.getChatMaxDistance());//room.RoomData.ChatMaxDistance);
        response.appendInt32(data.getChatFloodProtection());//room.RoomData.ChatFloodProtection > 2 ? 2 : room.RoomData.ChatFloodProtection);
        response.appendBoolean(false); //allow_dyncats_checkbox
        response.appendInt32(data.getWhoCanMute());//room.RoomData.WhoCanMute);
        response.appendInt32(data.getWhoCanKick());//room.RoomData.WhoCanKick);
        response.appendInt32(data.getWhoCanBan());//room.RoomData.WhoCanBan);
	}

}
