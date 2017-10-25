package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GroupInfoMessageComposer extends MessageComposer {

    private Group group;
    private int userId;
    
    private boolean openNewWindow;
    
    private boolean isOwner;
    private boolean isAdmin;
    
    public GroupInfoMessageComposer(Group group, Player player, boolean openNewWindow) {
        this.group = group;
        this.userId = player.getEntityId();
        this.openNewWindow = openNewWindow;
        
        this.isOwner = this.group.getOwnerId() == this.userId;
        this.isAdmin = this.group.getMemberManager().isMemberType(this.userId, GroupMemberType.ADMINISTRATOR);
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.GroupInfoMessageComposer);
        response.writeInt(group.getId());
        response.writeBool(true);
        response.writeInt(group.getAccessType().getType());
        response.writeString(group.getTitle());
        response.writeString(group.getDescription());
        response.writeString(group.getBadge());
        response.writeInt(group.getRoomId());
        response.writeString(RoomDao.getRoom(group.getRoomId(), false).getData().getName());
        
        if (isAdmin) {
            response.writeInt(3);
        } else if (group.getMemberManager().isMemberType(userId, GroupMemberType.REQUEST)) {
            response.writeInt(2);
        } else if (group.getMemberManager().isMemberType(userId, GroupMemberType.MEMBER)) {
            response.writeInt(1);
        } else {
            response.writeInt(0);
        }
        
        response.writeInt(group.getMemberManager().getMemberSize());
        response.writeBool(false);
        response.writeString("1-1-1970");
        response.writeBool(isOwner);
        response.writeBool(group.getMemberManager().isMemberType(userId, GroupMemberType.ADMINISTRATOR));
        response.writeString(PlayerManager.getInstance().getPlayerData(group.getOwnerId()).getName());
        response.writeBool(openNewWindow); 
        response.writeBool(group.canMembersDecorate());
        response.writeInt((isAdmin || isOwner) ? group.getMemberManager().getMembersByType(GroupMemberType.REQUEST).size() : 0); // Users waiting to be accepted (if any!)
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupInfoMessageComposer;
    }
}
