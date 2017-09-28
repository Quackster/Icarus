package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class GroupInfoComposer extends MessageComposer {

    private Group group;
    private int userId;
    
    private boolean openNewWindow;
    
    private boolean isOwner;
    private boolean isAdmin;
    
    public GroupInfoComposer(Group group, Player player, boolean openNewWindow) {
        this.group = group;
        this.userId = player.getEntityId();
        this.openNewWindow = openNewWindow;
        
        this.isOwner = this.group.getOwnerId() == this.userId;
        this.isAdmin = this.group.getMemberManager().isMemberType(this.userId, GroupMemberType.ADMINISTRATOR);
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
        
        if (this.isAdmin) {
            this.response.writeInt(3);
        } else if (this.group.getMemberManager().isMemberType(this.userId, GroupMemberType.REQUEST)) {
            this.response.writeInt(2);
        } else if (this.group.getMemberManager().isMemberType(this.userId, GroupMemberType.MEMBER)) {
            this.response.writeInt(1);
        } else {
            this.response.writeInt(0);
        }
        
        this.response.writeInt(this.group.getMemberManager().getMemberSize());
        this.response.writeBool(false);
        this.response.writeString("1-1-1970");
        this.response.writeBool(this.isOwner);
        this.response.writeBool(this.group.getMemberManager().isMemberType(this.userId, GroupMemberType.ADMINISTRATOR));
        this.response.writeString(PlayerManager.getPlayerData(this.group.getOwnerId()).getName());
        this.response.writeBool(this.openNewWindow); 
        this.response.writeBool(this.group.canMembersDecorate());
        this.response.writeInt((this.isAdmin || this.isOwner) ? this.group.getMemberManager().getMembersByType(GroupMemberType.REQUEST).size() : 0); // Users waiting to be accepted (if any!)
        this.response.writeBool(false);
    }
}
