package org.alexdev.icarus.game.groups.members;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.game.groups.Group;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class GroupMemberManager {
    
    private Group group;
    private Map<GroupMemberType, List<Integer>> groupMembers;

    public GroupMemberManager(Group group) {
        this.group = group;
        this.groupMembers = Maps.newHashMap();
        
        for (GroupMemberType memberType : GroupMemberType.values()) {
            this.groupMembers.put(memberType, Lists.newArrayList());
        }
    }

    /**
     * Gets the members by type.
     *
     * @param memberType the member type
     * @return the members by type
     */
    public List<Integer> getMembersByType(GroupMemberType memberType) {
        return this.groupMembers.get(memberType);
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
     * Gets the member size.
     *
     * @param type the type
     * @return the member size
     */
    public int getMemberSize(GroupMemberType type) {
        return this.groupMembers.get(type).size();
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
