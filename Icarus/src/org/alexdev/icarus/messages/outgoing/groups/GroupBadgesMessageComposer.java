package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class GroupBadgesMessageComposer extends MessageComposer {

    private int groupId;
    private String badge;

    public GroupBadgesMessageComposer(int groupId, String badge) {
        this.groupId = groupId;
        this.badge = badge;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GroupBadgesMessageComposer);
        this.response.writeInt(1);
        this.response.writeInt(this.groupId);
        this.response.writeString(this.badge);
    }

}
