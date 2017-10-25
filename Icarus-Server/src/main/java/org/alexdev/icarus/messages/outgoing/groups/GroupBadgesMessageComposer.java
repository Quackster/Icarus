package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class GroupBadgesMessageComposer extends MessageComposer {

    private int groupId;
    private String badge;

    public GroupBadgesMessageComposer(int groupId, String badge) {
        this.groupId = groupId;
        this.badge = badge;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(1);
        response.writeInt(this.groupId);
        response.writeString(this.badge);
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupBadgesMessageComposer;
    }
}