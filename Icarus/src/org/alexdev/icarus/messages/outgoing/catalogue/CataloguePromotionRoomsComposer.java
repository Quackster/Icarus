package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class CataloguePromotionRoomsComposer extends MessageComposer {

    private List<Room> rooms;

    public CataloguePromotionRoomsComposer(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.AvailablePromotionRoomsComposer);
        this.response.writeBool(true);
        this.response.writeInt(this.rooms.size());
        
        for (Room room : this.rooms) {
            
            this.response.writeInt(room.getData().getId());
            this.response.writeString(room.getData().getName());
            this.response.writeBool(false);
        }
    }

}
