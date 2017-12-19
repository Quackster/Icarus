package org.alexdev.icarus.messages.outgoing.groups;

import java.util.List;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CreateGroupDialogComposer extends MessageComposer {

    private List<Room> roomsWithoutGroup;

    public CreateGroupDialogComposer(List<Room> roomsWithoutGroup) {
        this.roomsWithoutGroup = roomsWithoutGroup;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.CreateGroupDialogComposer);
        response.writeInt(100); // Groups cost
        response.writeInt(this.roomsWithoutGroup.size());

        for (Room room : this.roomsWithoutGroup) {
            response.writeInt(room.getData().getId());
            response.writeString(room.getData().getName());
            response.writeBool(false);
        }

        for (int i = 0; i < 2; i++) {
            response.writeInt(5);
        }

        response.writeInt(11);
        response.writeInt(4);
        response.writeInt(6);
        response.writeInt(11);
        response.writeInt(4);

        for (int i = 0; i < 9; i++) {
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.CreateGroupDialogComposer;
    }
}
