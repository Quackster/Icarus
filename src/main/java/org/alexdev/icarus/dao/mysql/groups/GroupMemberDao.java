package org.alexdev.icarus.dao.mysql.groups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.groups.members.GroupMemberType;

public class GroupMemberDao {

    /**
     * Creates the group member.
     *
     * @param userId the user id
     * @param groupId the group id
     * @param memberType the member type
     */
    public static void createGroupMember(int userId, int groupId, GroupMemberType memberType) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO group_members (user_id, group_id, member_type) VALUES (?, ?, ?)", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, groupId);
            preparedStatement.setString(3, memberType.name());
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
    }

    /**
     * Gets the members.
     *
     * @param groupId the group id
     * @return the members
     */
    public static Map<GroupMemberType, List<Integer>> getMembers(int groupId) {
        
        Map<GroupMemberType, List<Integer>> groupMembers = new HashMap<>();

        for (GroupMemberType memberType : GroupMemberType.values()) {
            groupMembers.put(memberType, new ArrayList<>());
        }
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT user_id, member_type FROM group_members WHERE group_id = ?", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                GroupMemberType memberType = GroupMemberType.valueOf(resultSet.getString("member_type"));
                groupMembers.get(memberType).add(resultSet.getInt("user_id"));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return groupMembers;
    }
    
    /**
     * Delete member.
     *
     * @param groupId the group id
     * @param userId the user id
     */
    public static void deleteMember(int groupId, int userId) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("DELETE FROM group_members WHERE user_id = ? AND group_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, groupId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
    }
}
