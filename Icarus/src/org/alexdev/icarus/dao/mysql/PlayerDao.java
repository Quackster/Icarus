package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.factories.PlayerFactory;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;

public class PlayerDao {

	public static PlayerDetails getDetails(int userId) {

		PlayerDetails details = PlayerFactory.newDetails();
		Player player = PlayerManager.findById(userId);

		if (player != null) {
			details = player.getDetails();
		} else {

			Connection sqlConnection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				sqlConnection = Dao.getStorage().getConnection();
				
				preparedStatement = Dao.getStorage().prepare("SELECT id, username, rank, sso_ticket, motto, figure, credits FROM users WHERE id = ? LIMIT 1", sqlConnection);
				preparedStatement.setInt(1, userId);
				
				resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					fill(details, resultSet);
				}

			} catch (Exception e) {
				Log.exception(e);
			} finally {
				Storage.closeSilently(resultSet);
				Storage.closeSilently(preparedStatement);
				Storage.closeSilently(sqlConnection);
			}
		}

		return details;
	}

	public static boolean login(Player player, String ssoTicket) {
		
		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT id, username, rank, sso_ticket, motto, figure, credits FROM users WHERE sso_ticket = ? LIMIT 1", sqlConnection);
			preparedStatement.setString(1, ssoTicket);
			
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				fill(player.getDetails(), resultSet);
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

	public static int getId(String username) {

		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT id FROM users WHERE username = ? LIMIT 1", sqlConnection);
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				return resultSet.getInt("id");
			}
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}

		return -1;	
	}
	
	public static PlayerDetails fill(PlayerDetails details, ResultSet row) throws SQLException {
		details.fill(row.getInt("id"), row.getString("username"), row.getString("motto"),  row.getString("figure"), row.getInt("rank"), row.getInt("credits"));
		return details;
	}
}
