package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;

public class PlayerDao {

	public static PlayerDetails getDetails(int userId) {

		PlayerDetails details = new PlayerDetails(null);
		Player player = PlayerManager.findById(userId);

		if (player != null) {
			details = player.getDetails();
		} else {

			Connection sqlConnection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {

				sqlConnection = Dao.getStorage().getConnection();
				
				preparedStatement = Dao.getStorage().prepare("SELECT id, username, rank, sso_ticket, mission, figure, credits FROM users WHERE id = ? LIMIT 1", sqlConnection);
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
		
	    boolean success = false;
	    
		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT id, username, rank, sso_ticket, mission, figure, credits FROM users WHERE sso_ticket = ? LIMIT 1", sqlConnection);
			preparedStatement.setString(1, ssoTicket);
			
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				fill(player.getDetails(), resultSet);
				success = true;
			}

		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}

		return success;
	}

	public static int getId(String username) {

	    int id = -1;
	    
		Connection sqlConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			sqlConnection = Dao.getStorage().getConnection();
			preparedStatement = Dao.getStorage().prepare("SELECT id FROM users WHERE username = ? LIMIT 1", sqlConnection);
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				id = resultSet.getInt("id");
			}
		} catch (Exception e) {
			Log.exception(e);
		} finally {
			Storage.closeSilently(resultSet);
			Storage.closeSilently(preparedStatement);
			Storage.closeSilently(sqlConnection);
		}

		return id;	
	}
	
	public static PlayerDetails fill(PlayerDetails details, ResultSet row) throws SQLException {
		details.fill(row.getInt("id"), row.getString("username"), row.getString("mission"),  row.getString("figure"), row.getInt("rank"), row.getInt("credits"));
		return details;
	}
}
