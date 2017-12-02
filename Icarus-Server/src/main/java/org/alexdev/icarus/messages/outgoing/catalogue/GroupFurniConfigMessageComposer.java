package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

import java.util.List;

public class GroupFurniConfigMessageComposer extends MessageComposer {

    private List<Group> groups;

    public GroupFurniConfigMessageComposer(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.groups.size());
        for (Group group : this.groups) {
            response.writeInt(group.getId());
            response.writeString(group.getTitle());
            response.writeString(group.getBadge());
            response.writeString(group.getColourA());
            response.writeString(group.getColourB());
            response.writeBool(false);
            response.writeInt(group.getOwnerId());
            response.writeBool(false);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.RequireGroupComposer;
    }
}