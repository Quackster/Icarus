package org.alexdev.icarus.dao.mysql.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;

public class MessengerDao {

    public static List<MessengerUser> getFriends(int userID) {

        List<MessengerUser> friends = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_friendships WHERE (sender = " + userID + ") OR (receiver = " + userID + ")", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                MessengerUser friend = null;

                if (resultSet.getInt("sender") != userID) {
                    friend = new MessengerUser(resultSet.getInt("sender"));
                } else {
                    friend = new MessengerUser(resultSet.getInt("receiver"));
                }

                friends.add(friend);
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return friends;
    }


    public static List<MessengerUser> getRequests(int userID) {

        List<MessengerUser> users = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_requests WHERE to_id = " + userID, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(new MessengerUser(resultSet.getInt("from_id")));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return users;
    }

    public static List<Integer> search(String query) {

        List<Integer> users = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("SELECT id FROM users WHERE username LIKE ? LIMIT 30", sqlConnection);
            preparedStatement.setString(1, query + "%");

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(resultSet.getInt("id"));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return users;
    }

    public static boolean newRequest(int fromID, int toID) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        if (!requestExists(fromID, toID)) {

            try {

                sqlConnection = Dao.getStorage().getConnection();
                preparedStatement = Dao.getStorage().prepare("INSERT INTO messenger_requests (to_id, from_id) VALUES (?, ?)", sqlConnection);
                preparedStatement.setInt(1, toID);
                preparedStatement.setInt(2, fromID);
                preparedStatement.execute();

                return true;

            } catch (SQLException e) {
                Log.exception(e);
            } finally {
                Storage.closeSilently(resultSet);
                Storage.closeSilently(preparedStatement);
                Storage.closeSilently(sqlConnection);
            }
        }

        return false;
    }

    public static boolean requestExists(int fromID, int toID) {

        boolean exists = false;
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_requests WHERE (to_id = '" + toID + "') AND (from_id = '" + fromID + "') OR (from_id = '" + toID + "') AND (to_id = '" + fromID + "')", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return exists;
    }

    public static void removeRequest(int fromID, int toID) {
        Dao.getStorage().execute("DELETE FROM messenger_requests WHERE from_id = " + fromID + " AND to_id = " + toID);
    }

    public static void removeFriend(int friendID, int userID) {
        Dao.getStorage().execute("DELETE FROM messenger_friendships WHERE (sender = " + userID + " AND receiver = " + friendID + ") OR (receiver = " + userID + " AND sender = " + friendID + ")");
    }

    public static boolean newFriend(int sender, int receiver) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO messenger_friendships (sender, receiver) VALUES (?, ?)", sqlConnection);
            preparedStatement.setInt(1, sender);
            preparedStatement.setInt(2, receiver);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return false;
    }
}
