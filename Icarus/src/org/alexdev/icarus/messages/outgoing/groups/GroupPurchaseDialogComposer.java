package org.alexdev.icarus.messages.outgoing.groups;

import java.util.List;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class GroupPurchaseDialogComposer extends MessageComposer {

    private List<Room> roomsWithoutGroup;

    public GroupPurchaseDialogComposer(List<Room> roomsWithoutGroup) {
        this.roomsWithoutGroup = roomsWithoutGroup;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GroupPurchaseDialogComposer);
        this.response.writeInt(100); // Groups cost
        this.response.writeInt(this.roomsWithoutGroup.size());
        
        for (Room room : this.roomsWithoutGroup) {
            this.response.writeInt(room.getData().getID());
            this.response.writeString(room.getData().getName());
            this.response.writeBool(false);
        }
        
        for (int i = 0; i < 2; i++) {
            this.response.writeInt(5);
        }
        
        this.response.writeInt(11);
        this.response.writeInt(4);
        this.response.writeInt(6);
        this.response.writeInt(11);
        this.response.writeInt(4);
        
        for (int i = 0; i < 9; i++) {
            this.response.writeInt(0);
        }
    }
}
