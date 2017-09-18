package org.alexdev.icarus.game.groups.members;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.groups.GroupMemberDao;
import org.alexdev.icarus.game.groups.Group;

import com.google.common.collect.Lists;

public class GroupMemberManager {
    
    private Group group;
    private Map<GroupMemberType, List<Integer>> groupMembers;

    public GroupMemberManager(Group group) {
        this.group = group;
        this.groupMembers = GroupMemberDao.getMembers(group.getId());
    }

    /**
     * Gets the members by type.
     *
     * @param memberType the member type
     * @return the members by type
     */
    public List<Integer> getMembersByType(GroupMemberType memberType) {
        return Lists.newArrayList(this.groupMembers.get(memberType));
    }
    
    /**
     * Gets the members by types.
     *
     * @param types the types
     * @return the members by types
     */
    public List<Integer> getMembersByTypes(GroupMemberType... types) {
        
        List<Integer> members = Lists.newArrayList();
        
        for (GroupMemberType memberType : types) {
            members.addAll(this.groupMembers.get(memberType));
        }
        
        return members;
    }
    
    /**
     * Checks if member is certain type.
     *
     * @param userId the user id
     * @param type the type
     * @return true, if the member is the type asked
     */
    public boolean isMemberType(int userId, GroupMemberType type) {
        return this.groupMembers.get(type).contains(userId);
    }
    
    /**
     * Checks if user is a member.
     *
     * @param userId the user id
     * @return true, if the user is a member
     */
    public boolean isMember(int userId) {

        for (Integer members : this.getMembers()) {
            if (members == userId) {
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Gets the members.
     *
     * @return the members
     */
    public List<Integer> getMembers() {
        return this.getMembersByTypes(GroupMemberType.ADMINISTRATOR, GroupMemberType.MEMBER);
    }
    
    /**
     * Gets the member size.
     *
     * @param type the type
     * @return the member size
     */
    public int getMemberSize() {
        return this.getMembers().size();
    }
    
    /**
     * Adds the member, but will remove all traces of the member
     * before adding them again.
     *
     * @param type the type
     * @param userId the user id
     */
    public void addMember(GroupMemberType type, int userId) {
        
        this.remove(userId);
        this.groupMembers.get(type).add(userId);
        
        GroupMemberDao.createGroupMember(userId, this.group.getId(), type);
    }
    
    /**
     * Removes all traces of the user.
     *
     * @param userId the user id
     */
    public void remove(int userId) {
        
        GroupMemberDao.deleteMember(this.group.getId(), userId);
        
        for (List<Integer> members : this.groupMembers.values()) {
            members.remove(Integer.valueOf(userId));
        }
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

}
