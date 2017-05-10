package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.alexdev.icarus.factories.MessengerFactory;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;

public class MessengerDao {

	public static List<MessengerUser> getFriends(int userId) {

		List<MessengerUser> friends = Lists.newArrayList();

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
					friend = MessengerFactory.getUser(resultSet.getInt("sender"));
				} else {
					friend = MessengerFactory.getUser(resultSet.getInt("receiver"));
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


	public static List<MessengerUser> getRequests(int userId) {

		List<MessengerUser> users = Lists.newArrayList();

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_requests WHERE to_id = " + userId, sqlConnection);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				users.add(MessengerFactory.getUser(resultSet.getInt("from_id")));
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

	public static boolean newRequest(int fromId, int toId) {

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		if (!requestExists(fromId, toId)) {

			try {

				sqlConnection = Dao.getStorage().getConnection();
				preparedStatement = Dao.getStorage().prepare("INSERT INTO messenger_requests (to_id, from_id) VALUES (?, ?)", sqlConnection);
				preparedStatement.setInt(1, toId);
				preparedStatement.setInt(2, fromId);
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

	public static boolean requestExists(int fromId, int toId) {

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT * FROM messenger_requests WHERE (to_id = '" + toId + "') AND (from_id = '" + fromId + "') OR (from_id = '" + toId + "') AND (to_id = '" + fromId + "')", sqlConnection);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}

		return false;
	}

	public static boolean removeRequest(int fromId, int toId) {
		return Dao.getStorage().execute("DELETE FROM messenger_requests WHERE from_id = " + fromId + " AND to_id = " + toId);
	}

	public static boolean removeFriend(int friendId, int userId) {
		return Dao.getStorage().execute("DELETE FROM messenger_friendships WHERE (sender = " + userId + " AND receiver = " + friendId + ") OR (receiver = " + userId + " AND sender = " + friendId + ")");
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
