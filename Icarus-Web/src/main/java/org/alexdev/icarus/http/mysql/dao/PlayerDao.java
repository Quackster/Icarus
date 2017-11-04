package org.alexdev.icarus.http.mysql.dao;

import org.alexdev.duckhttpd.util.WebUtilities;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.Storage;
import org.alexdev.icarus.http.util.Util;
import org.alexdev.icarus.http.util.config.Configuration;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerDao {

    public static boolean emailExists(String email) {

        boolean success = false;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT id FROM users WHERE email = ? LIMIT 1", sqlConnection);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                success = true;
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return success;
    }

    public static boolean nameExists(String name) {

        boolean success = false;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT id FROM users WHERE username = ? LIMIT 1", sqlConnection);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                success = true;
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return success;
    }

    public static void updateName(int userId, String name) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("UPDATE users SET username = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void updateFigure(int userId, String figure) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("UPDATE users SET figure = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, figure);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void updateTicket(int userId, Player player) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            String ssoTicket = "sso-icarus-" + Util.randomString(10) + "-" + Util.randomString(6) + "-" + Util.randomString(8);
            player.setSsoTicket(ssoTicket);

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("UPDATE users SET sso_ticket = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, ssoTicket);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static int create(String email, String password) {

        int userId = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("INSERT INTO `users` (username, password, email, mission, figure, credits, duckets, last_online, join_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, "");
            preparedStatement.setString(2, BCrypt.hashpw(password, BCrypt.gensalt()));
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, Configuration.REGISTER_MOTTO);
            preparedStatement.setString(5, Configuration.REGISTER_FIGURE);
            preparedStatement.setInt(6, Configuration.REGISTER_CREDITS);
            preparedStatement.setInt(7, Configuration.REGISTER_DUCKETS);
            preparedStatement.setLong(8, WebUtilities.currentTimeSeconds());
            preparedStatement.setLong(9, WebUtilities.currentTimeSeconds());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                userId = resultSet.getInt(1);
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return userId;
    }

    public static int valid(String email, String password) {

        int userId = 0;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT id, password FROM users WHERE email = ? LIMIT 1", sqlConnection);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (BCrypt.checkpw(password, resultSet.getString("password"))) {
                    userId = resultSet.getInt("id");
                }
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return userId;
    }

    public static Player get(int userId) {

        Player player = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();
            preparedStatement = Storage.get().prepare("SELECT * FROM users WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                player = new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("figure"),
                                    resultSet.getInt("credits"), resultSet.getInt("duckets"), resultSet.getString("email"),
                                    resultSet.getString("mission"), resultSet.getLong("last_online"), resultSet.getInt("rank"));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return player;
    }
}
