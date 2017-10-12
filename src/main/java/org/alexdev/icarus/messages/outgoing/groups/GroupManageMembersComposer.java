package org.alexdev.icarus.messages.outgoing.groups;

import java.util.List;

import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Util;

public class GroupManageMembersComposer extends MessageComposer {

    private Group group;
    private int page;
    private int requestType;
    private String searchQuery;
    private List<Integer> groupMembers;
    private boolean hasAccess;

    public GroupManageMembersComposer(Group group, int page, int requestType, String searchQuery, List<Integer> groupMembers, boolean hasAccess) {
        this.group = group;
        this.requestType = requestType;
        this.searchQuery = searchQuery;
        this.page = page;
        this.groupMembers = groupMembers;
        this.hasAccess = hasAccess;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GroupManageMembersComposer);
        this.response.writeInt(this.group.getId());
        this.response.writeString(this.group.getTitle());
        this.response.writeInt(this.group.getRoomId());
        this.response.writeString(this.group.getBadge());
        this.response.writeInt(this.groupMembers.size());

        if (groupMembers.size() == 0) {
            this.response.writeInt(0);
        } else {

            // Inspired by Comet... best way to do this in Java
            List<List<Integer>> paginatedMembers = Util.paginate(this.groupMembers, GameSettings.GROUP_MEMBERS_PER_PAGE);
            this.response.writeInt(paginatedMembers.get(this.page).size());

            for (int userId : paginatedMembers.get(this.page)) {
                
                PlayerDetails details = PlayerManager.getPlayerData(userId);
                
                if (this.group.getOwnerId() == userId) {
                    this.response.writeInt(0);
                } else if (this.group.getMemberManager().isMemberType(userId, GroupMemberType.ADMINISTRATOR)) {
                    this.response.writeInt(1);
                } else if (this.group.getMemberManager().isMemberType(userId, GroupMemberType.MEMBER)) {
                    this.response.writeInt(2);
                } else {
                    this.response.writeInt(3);
                }
                
                this.response.writeInt(userId);
                this.response.writeString(details.getName());
                this.response.writeString(details.getFigure());
                this.response.writeString("");
            }
        }
        
        this.response.writeBool(this.hasAccess);
        this.response.writeInt(GameSettings.GROUP_MEMBERS_PER_PAGE);
        this.response.writeInt(this.page);
        this.response.writeInt(this.requestType);
        this.response.writeString(this.searchQuery);
        
    }
}
