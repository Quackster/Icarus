package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomPromotionMessageComposer extends MessageComposer {

    private Room room;

    public RoomPromotionMessageComposer(Room room) {
        this.room = room;
    }
    
    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomPromotionMessageComposer);

        if (this.room.getPromotion() != null) {
            response.writeInt(room.getData().getId());
            response.writeInt(room.getData().getOwnerId());
            response.writeString(room.getData().getOwnerName());
            response.writeInt(1);
            response.writeInt(0);
            response.writeString(room.getPromotion().getPromotionName());
            response.writeString(room.getPromotion().getPromotionDescription());
        } else {
            response.writeInt(-1);
            response.writeInt(-1);
            response.writeString("");
            response.writeInt(0);
            response.writeInt(0);
            response.writeString("");
            response.writeString("");
        }

        response.writeInt(0);
        response.writeInt(0);
        response.writeInt(0);
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomPromotionMessageComposer;
    }
}
