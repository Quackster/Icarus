package org.alexdev.icarus.messages.outgoing.groups.members;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class UnknownGroupComposer extends MessageComposer {

    private int groupId;
    private int userId;

    public UnknownGroupComposer(int groupId, int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.UnknownGroupComposer);
        this.response.writeInt(this.groupId);
        this.response.writeInt(this.userId);

    }

}
