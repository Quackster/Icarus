package org.alexdev.icarus.dao.mysql.groups;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.access.GroupAccessType;
import org.alexdev.icarus.log.Log;

public class GroupDao {

    public static Group createGroup(String title, String description, String badge, int ownerId, int roomId, long created, int colourA, int colourB) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        int groupId = -1;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO group_data (title, description, badge, owner_id, room_id, created, colour_a, colour_b) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, badge);
            preparedStatement.setInt(4, ownerId);
            preparedStatement.setInt(5, roomId);
            preparedStatement.setLong(6, created);
            preparedStatement.setInt(7, colourA);
            preparedStatement.setInt(8, colourB);
            preparedStatement.execute();
            
            ResultSet row = preparedStatement.getGeneratedKeys();

            if (row != null && row.next()) {
                groupId = row.getInt(1);
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return new Group(groupId, title, description, badge, ownerId, roomId, created, colourA, colourB, false, GroupAccessType.OPEN);
    }

    public static Group getGroup(int groupId) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        Group group = null;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM group_data WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, groupId);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                group = new Group(groupId, resultSet.getString("title"), resultSet.getString("description"), resultSet.getString("badge"), resultSet.getInt("owner_id"), resultSet.getInt("room_id"), resultSet.getInt("created"), resultSet.getInt("colour_a"), resultSet.getInt("colour_b"), resultSet.getInt("can_members_decorate") == 1, GroupAccessType.valueOf(resultSet.getString("access_type")));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return group;
    }
    
    public static void deleteGroup(int id) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("DELETE FROM group_data WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
    }

    public static void saveGroup(Group group) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("UPDATE group_data SET title = ?, description = ?, badge = ?, room_id = ?, colour_a = ?, colour_b = ?, access_type = ?, can_members_decorate = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, group.getTitle());
            preparedStatement.setString(2, group.getDescription());
            preparedStatement.setString(3, group.getBadge());
            preparedStatement.setInt(4, group.getRoomId());
            preparedStatement.setInt(5, group.getColourA());
            preparedStatement.setInt(6, group.getColourB());
            preparedStatement.setString(7, group.getAccessType().name());
            preparedStatement.setInt(8, group.canMembersDecorate() ? 1 : 0);
            preparedStatement.setInt(9, group.getId());
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
