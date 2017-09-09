package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomPromotionMessageComposer extends MessageComposer {

    private Room room;

    public RoomPromotionMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.RoomPromotionMessageComposer);

        if (this.room.getPromotion() != null) {
            this.response.writeInt(room.getData().getID());
            this.response.writeInt(room.getData().getOwnerID());
            this.response.writeString(room.getData().getOwnerName());
            this.response.writeInt(1);
            this.response.writeInt(0);
            this.response.writeString(room.getPromotion().getPromotionName());
            this.response.writeString(room.getPromotion().getPromotionDescription());
        } else {
            this.response.writeInt(-1);
            this.response.writeInt(-1);
            this.response.writeString("");
            this.response.writeInt(0);
            this.response.writeInt(0);
            this.response.writeString("");
            this.response.writeString("");
        }

        this.response.writeInt(0);
        this.response.writeInt(0);
        this.response.writeInt(0);//Unknown, came in build RELEASE63-201411181343-400753188
    }
}
