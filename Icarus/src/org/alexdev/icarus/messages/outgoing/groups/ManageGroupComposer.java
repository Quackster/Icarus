package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.util.Util;

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
        
        for (int i = 0; i < badgeParts.length; i++) {
            
            String text = badgeParts[i];

            int num1 = (text.length() >= 6) ? Integer.parseInt(Util.left(text, 3)) : Integer.parseInt(Util.left(text, 2));
            int num2 = (text.length() >= 6) ? Integer.parseInt(Util.left(Util.right(text, 3), 2)) : Integer.parseInt(Util.right(Util.left(text, 4), 2));

            this.response.writeInt(num1);
            this.response.writeInt(num2);

            if (text.length() < 5) {
                this.response.writeInt(0);
            } else if (text.length() >= 6) {
                this.response.writeInt(Integer.parseInt(Util.right(text, 1)));
            } else {
                this.response.writeInt(Integer.parseInt(Util.right(text, 1)));
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
