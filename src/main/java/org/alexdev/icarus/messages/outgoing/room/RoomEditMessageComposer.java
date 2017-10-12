package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class RoomEditMessageComposer extends MessageComposer {

    private RoomData data;

    public RoomEditMessageComposer(RoomData data) {
        this.data = data;
    }

    @Override
    public void write() {  
        this.response.init(Outgoing.RoomSettingsDataMessageComposer);
        this.response.writeInt(data.getId());
        this.response.writeString(data.getName());
        this.response.writeString(data.getDescription());
        this.response.writeInt(data.getState().getStateCode());
        this.response.writeInt(data.getCategory());
        this.response.writeInt(data.getUsersMax());
        this.response.writeInt(data.getUsersMax());
        this.response.writeInt(data.getTags().length);
        
        for (String tag : data.getTags()) {
            this.response.writeString(tag);
        }
        
        this.response.writeInt(data.getTradeState());
        this.response.writeInt(data.isAllowPets());
        this.response.writeInt(data.isAllowPetsEat());
        this.response.writeInt(data.isAllowWalkthrough());
        this.response.writeInt(data.hasHiddenWall());
        this.response.writeInt(data.getWallThickness());
        this.response.writeInt(data.getFloorThickness());
        this.response.writeInt(data.getBubbleMode());//room.RoomData.ChatType);
        this.response.writeInt(data.getBubbleType());//room.RoomData.ChatBalloon);
        this.response.writeInt(data.getBubbleScroll());//room.RoomData.ChatSpeed);
        this.response.writeInt(data.getChatMaxDistance());//room.RoomData.ChatMaxDistance);
        this.response.writeInt(data.getChatFloodProtection());//room.RoomData.ChatFloodProtection > 2 ? 2 : room.RoomData.ChatFloodProtection);
        this.response.writeBool(false); //allow_dyncats_checkbox
        this.response.writeInt(data.getWhoCanMute());//room.RoomData.WhoCanMute);
        this.response.writeInt(data.getWhoCanKick());//room.RoomData.WhoCanKick);
        this.response.writeInt(data.getWhoCanBan());//room.RoomData.WhoCanBan);
    }
}
