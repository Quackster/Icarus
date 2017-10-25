package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomEditMessageComposer extends MessageComposer {

    private RoomData data;

    public RoomEditMessageComposer(RoomData data) {
        this.data = data;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomSettingsDataMessageComposer);
        response.writeInt(data.getId());
        response.writeString(data.getName());
        response.writeString(data.getDescription());
        response.writeInt(data.getState().getStateCode());
        response.writeInt(data.getCategory());
        response.writeInt(data.getUsersMax());
        response.writeInt(data.getUsersMax());
        response.writeInt(data.getTags().length);

        for (String tag : data.getTags()) {
            response.writeString(tag);
        }

        response.writeInt(data.getTradeState());
        response.writeInt(data.isAllowPets());
        response.writeInt(data.isAllowPetsEat());
        response.writeInt(data.isAllowWalkthrough());
        response.writeInt(data.hasHiddenWall());
        response.writeInt(data.getWallThickness());
        response.writeInt(data.getFloorThickness());
        response.writeInt(data.getBubbleMode());//room.RoomData.ChatType);
        response.writeInt(data.getBubbleType());//room.RoomData.ChatBalloon);
        response.writeInt(data.getBubbleScroll());//room.RoomData.ChatSpeed);
        response.writeInt(data.getChatMaxDistance());//room.RoomData.ChatMaxDistance);
        response.writeInt(data.getChatFloodProtection());//room.RoomData.ChatFloodProtection > 2 ? 2 : room.RoomData.ChatFloodProtection);
        response.writeBool(false); //allow_dyncats_checkbox
        response.writeInt(data.getWhoCanMute());//room.RoomData.WhoCanMute);
        response.writeInt(data.getWhoCanKick());//room.RoomData.WhoCanKick);
        response.writeInt(data.getWhoCanBan());//room.RoomData.WhoCanBan);
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomSettingsDataMessageComposer;
    }
}
