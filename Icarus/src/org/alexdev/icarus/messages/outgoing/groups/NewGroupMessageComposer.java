package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class NewGroupMessageComposer extends MessageComposer {

    private int roomId;
    private int groupId;

    public NewGroupMessageComposer(int roomId, int groupId) {
        this.roomId = roomId;
        this.groupId = groupId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GroupInfoMessageComposer);
        this.response.writeInt(this.roomId);
        this.response.writeInt(this.groupId);
    }

}
