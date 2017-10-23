package org.alexdev.icarus.dao.mysql.messenger.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.messenger.MessengerUser;

public class MessengerDao {

    /**
     * Gets the friends.
     *
     * @param userId the user id
     * @return the friends
     */
    public static List<MessengerUser> getFriends(int userId) {

        List<MessengerUser> friends = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_friendships WHERE (sender = " + userId + ") OR (receiver = " + userId + ")", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                MessengerUser friend = null;

                if (resultSet.getInt("sender") != userId) {
                    friend = new MessengerUser(resultSet.getInt("sender"));
                } else {
                    friend = new MessengerUser(resultSet.getInt("receiver"));
                }

                friends.add(friend);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return friends;
    }


    /**
     * Gets the requests.
     *
     * @param userId the user id
     * @return the requests
     */
    public static List<MessengerUser> getRequests(int userId) {

        List<MessengerUser> users = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_requests WHERE to_id = " + userId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(new MessengerUser(resultSet.getInt("from_id")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return users;
    }

    /**
     * Search.
     *
     * @param query the query
     * @return the list
     */
    public static List<Integer> search(String query) {

        List<Integer> users = new ArrayList<>();

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
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return users;
    }

    /**
     * New request.
     *
     * @param fromId the from id
     * @param toId the to id
     * @return true, if successful
     */
    public static boolean newRequest(int fromId, int toId) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean success = false;
        
        if (!requestExists(fromId, toId)) {

            try {

                sqlConnection = Dao.getStorage().getConnection();
                preparedStatement = Dao.getStorage().prepare("INSERT INTO messenger_requests (to_id, from_id) VALUES (?, ?)", sqlConnection);
                preparedStatement.setInt(1, toId);
                preparedStatement.setInt(2, fromId);
                preparedStatement.execute();
                success = true;
            } catch (SQLException e) {
                Storage.logError(e);
            } finally {
                Storage.closeSilently(resultSet);
                Storage.closeSilently(preparedStatement);
                Storage.closeSilently(sqlConnection);
            }
        }

        return success;
    }

    /**
     * Request exists.
     *
     * @param fromId the from id
     * @param toId the to id
     * @return true, if successful
     */
    public static boolean requestExists(int fromId, int toId) {

        boolean exists = false;
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_requests WHERE (to_id = '" + toId + "') AND (from_id = '" + fromId + "') OR (from_id = '" + toId + "') AND (to_id = '" + fromId + "')", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return exists;
    }

    /**
     * Removes the request.
     *
     * @param fromId the from id
     * @param toId the to id
     */
    public static void removeRequest(int fromId, int toId) {
        Dao.getStorage().execute("DELETE FROM messenger_requests WHERE from_id = " + fromId + " AND to_id = " + toId);
    }

    /**
     * Removes the friend.
     *
     * @param friendId the friend id
     * @param userId the user id
     */
    public static void removeFriend(int friendId, int userId) {
        Dao.getStorage().execute("DELETE FROM messenger_friendships WHERE (sender = " + userId + " AND receiver = " + friendId + ") OR (receiver = " + userId + " AND sender = " + friendId + ")");
    }

    /**
     * New friend.
     *
     * @param sender the sender
     * @param receiver the receiver
     * @return true, if successful
     */
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
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return false;
    }
}
