package org.alexdev.icarus.messages.outgoing.groups;

import java.util.List;

import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.util.Util;

public class GroupManageMembersComposer extends MessageComposer {

    private static final int GROUP_MEMBERS_PER_PAGE = 14;

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
    public void compose(Response response) {
        response.writeInt(group.getId());
        response.writeString(group.getTitle());
        response.writeInt(group.getRoomId());
        response.writeString(group.getBadge());
        response.writeInt(groupMembers.size());

        if (groupMembers.size() == 0) {
            response.writeInt(0);
        } else {

            // Inspired by Comet... best way to do this in Java
            List<List<Integer>> paginatedMembers = Util.paginate(groupMembers, GROUP_MEMBERS_PER_PAGE);
            response.writeInt(paginatedMembers.get(page).size());

            for (int userId : paginatedMembers.get(page)) {
                
                PlayerDetails details = PlayerManager.getPlayerData(userId);
                
                if (group.getOwnerId() == userId) {
                    response.writeInt(0);
                } else if (group.getMemberManager().isMemberType(userId, GroupMemberType.ADMINISTRATOR)) {
                    response.writeInt(1);
                } else if (group.getMemberManager().isMemberType(userId, GroupMemberType.MEMBER)) {
                    response.writeInt(2);
                } else {
                    response.writeInt(3);
                }
                
                response.writeInt(userId);
                response.writeString(details.getName());
                response.writeString(details.getFigure());
                response.writeString("");
            }
        }
        
        response.writeBool(hasAccess);
        response.writeInt(GROUP_MEMBERS_PER_PAGE);
        response.writeInt(page);
        response.writeInt(requestType);
        response.writeString(searchQuery);
        
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupManageMembersComposer;
    }
}