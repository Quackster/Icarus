package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomSettingsDataMessageComposer extends MessageComposer {

    private Room room;

    public RoomSettingsDataMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        
        RoomData data = this.room.getData();
        
        this.response.init(Outgoing.RoomSettingsDataMessageComposer);
        this.response.writeInt(data.getId());
        this.response.writeString(data.getName());
        this.response.writeString(data.getDescription());
        this.response.writeInt(data.getState().getStateCode());
        this.response.writeInt(data.getCategory());
        this.response.writeInt(data.getUsersMax());
        this.response.writeInt(data.getUsersMax());
        this.response.writeInt(0);
        /*foreach (var s in room.RoomData.Tags)
        {
            this.response.appendString(s);
        }*/
        this.response.writeInt(data.getTradeState());
        this.response.writeInt(data.isAllowPets());
        this.response.writeInt(data.isAllowPetsEat());
        this.response.writeInt(data.isAllowWalkthrough());
        this.response.writeInt(data.isHideWall());
        this.response.writeInt(data.getWallThickness());
        this.response.writeInt(data.getFloorThickness());
        this.response.writeInt(data.getChatMode());//room.RoomData.ChatType);
        this.response.writeInt(data.getChatSize());//room.RoomData.ChatBalloon);
        this.response.writeInt(data.getChatSpeed());//room.RoomData.ChatSpeed);
        this.response.writeInt(data.getChatMaxDistance());//room.RoomData.ChatMaxDistance);
        this.response.writeInt(data.getChatFloodProtection());//room.RoomData.ChatFloodProtection > 2 ? 2 : room.RoomData.ChatFloodProtection);
        this.response.writeBool(false); //allow_dyncats_checkbox
        this.response.writeInt(data.getWhoCanMute());//room.RoomData.WhoCanMute);
        this.response.writeInt(data.getWhoCanKick());//room.RoomData.WhoCanKick);
        this.response.writeInt(data.getWhoCanBan());//room.RoomData.WhoCanBan);
    }
}
