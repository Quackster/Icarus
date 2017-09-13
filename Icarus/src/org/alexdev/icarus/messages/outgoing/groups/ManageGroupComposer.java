package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class ManageGroupComposer extends MessageComposer {

    private Group group;

    public ManageGroupComposer(Group group) {
        this.group = group;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.ManageGroupComposer);
        this.response.writeInt(0);
        this.response.writeBool(true);
        this.response.writeInt(this.group.getId());
        this.response.writeString(this.group.getTitle());
        this.response.writeString(this.group.getDescription());
        this.response.writeInt(1);
        this.response.writeInt(this.group.getColourA());
        this.response.writeInt(this.group.getColourB());
        this.response.writeInt(this.group.getAccessType().getType());
        this.response.writeInt(this.group.canMembersDecorate());
        this.response.writeBool(false);
        this.response.writeString("");
        this.response.writeInt(5);
        
        String[] badgeParts = this.group.getBadge().replace("b", "").split("s");
        
        for (String symbol : badgeParts) {

            this.response.writeInt((symbol.length() >= 6 ? Integer.valueOf(symbol.substring(0, 3)) : Integer.valueOf(symbol.substring(0, 2))));
            this.response.writeInt((symbol.length() >= 6 ? Integer.valueOf(symbol.substring(3, 5)) : Integer.valueOf(symbol.substring(2, 4))));

            if (symbol.length() < 5) {
                this.response.writeInt(0);
            } else if (symbol.length() >= 6) {
                this.response.writeInt(Integer.valueOf(symbol.substring(5, 6)));
            } else {
                this.response.writeInt(Integer.valueOf(symbol.substring(4, 5)));
            }
        }

        int i = 0;
        while (i < (5 - badgeParts.length)) {
            this.response.writeInt(0);
            this.response.writeInt(0);
            this.response.writeInt(0);
            i++;
        }

        this.response.writeString(this.group.getBadge());
        this.response.writeInt(0); // Member count
    }
}
