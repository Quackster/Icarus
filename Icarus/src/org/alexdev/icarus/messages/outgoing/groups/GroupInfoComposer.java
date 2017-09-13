package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class GroupInfoComposer extends MessageComposer {

    private Group group;
    private Player player;
    private boolean newWindow;

    public GroupInfoComposer(Group group, Player player, boolean newWindow) {
        this.group = group;
        this.player = player;
        this.newWindow = newWindow;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GroupInfoComposer);
        this.response.writeInt(this.group.getId());
        this.response.writeBool(true);
        this.response.writeInt(group.getAccessType().getType());
        this.response.writeString(this.group.getTitle());
        this.response.writeString(this.group.getDescription());
        this.response.writeString(this.group.getBadge());
        this.response.writeInt(this.group.getRoomId());
        this.response.writeString(RoomDao.getRoom(group.getRoomId(), false).getData().getName());
        this.response.writeInt(0); // Member type
        this.response.writeInt(0); // Members
        this.response.writeBool(false);
        this.response.writeString("1-1-1970");
        this.response.writeBool(this.group.getOwnerId() == player.getDetails().getId());
        this.response.writeBool(false); // admin
        this.response.writeString(PlayerManager.getPlayerData(this.group.getOwnerId()).getName());
        this.response.writeBool(this.newWindow); 
        this.response.writeBool(this.group.canMembersDecorate());
        this.response.writeInt(0); // Pending users
        this.response.writeBool(false);//HabboTalk.
    }
}
