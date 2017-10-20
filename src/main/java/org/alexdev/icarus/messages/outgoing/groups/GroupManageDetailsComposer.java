package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GroupManageDetailsComposer extends MessageComposer {

    private Group group;

    public GroupManageDetailsComposer(Group group) {
        this.group = group;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(0);
        response.writeBool(true);
        response.writeInt(group.getId());
        response.writeString(group.getTitle());
        response.writeString(group.getDescription());
        response.writeInt(1);
        response.writeInt(group.getColourA());
        response.writeInt(group.getColourB());
        response.writeInt(group.getAccessType().getType());
        response.writeInt(group.canMembersDecorate());
        response.writeBool(false);
        response.writeString("");
        response.writeInt(5);

        String[] badgeParts = group.getBadge().replace("b", "").split("s");

        for (String symbol : badgeParts) {

            response.writeInt((symbol.length() >= 6 ? Integer.valueOf(symbol.substring(0, 3)) : Integer.valueOf(symbol.substring(0, 2))));
            response.writeInt((symbol.length() >= 6 ? Integer.valueOf(symbol.substring(3, 5)) : Integer.valueOf(symbol.substring(2, 4))));

            if (symbol.length() < 5) {
                response.writeInt(0);
            } else if (symbol.length() >= 6) {
                response.writeInt(Integer.valueOf(symbol.substring(5, 6)));
            } else {
                response.writeInt(Integer.valueOf(symbol.substring(4, 5)));
            }
        }

        int i = 0;
        while (i < (5 - badgeParts.length)) {
            response.writeInt(0);
            response.writeInt(0);
            response.writeInt(0);
            i++;
        }

        response.writeString(group.getBadge());
        response.writeInt(group.getMemberManager().getMemberSize()); // Member count
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupManageDetailsComposer;
    }
}
