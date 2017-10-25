package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CataloguePromotionRoomsComposer extends MessageComposer {

    private List<Room> rooms;

    public CataloguePromotionRoomsComposer(List<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.AvailablePromotionRoomsComposer);
        response.writeBool(true);
        response.writeInt(this.rooms.size());

        for (Room room : this.rooms) {

            response.writeInt(room.getData().getId());
            response.writeString(room.getData().getName());
            response.writeBool(false);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.AvailablePromotionRoomsComposer;
    }
}