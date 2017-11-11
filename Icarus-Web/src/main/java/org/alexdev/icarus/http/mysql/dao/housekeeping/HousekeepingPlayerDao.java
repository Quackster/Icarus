package org.alexdev.icarus.http.mysql.dao.housekeeping;

import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingPlayerDao {

    public static List<Player> getPlayers(int page) {

        List<Player> players = new ArrayList<>();

        int rows = 25;
        int nextOffset = page * rows;

        if (nextOffset >= 0) {
            Connection sqlConnection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {

                sqlConnection = Storage.get().getConnection();
                preparedStatement = Storage.get().prepare("SELECT * FROM users ORDER BY join_date DESC LIMIT ? OFFSET ?", sqlConnection);
                preparedStatement.setInt(1, rows);
                preparedStatement.setInt(2, nextOffset);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    players.add(new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("figure"),
                            resultSet.getInt("credits"), resultSet.getInt("duckets"), resultSet.getString("email"),
                            resultSet.getString("mission"), resultSet.getLong("last_online"), resultSet.getInt("rank"),
                            resultSet.getLong("join_date")));
                }

            } catch (Exception e) {
                Storage.logError(e);
            } finally {
                Storage.closeSilently(resultSet);
                Storage.closeSilently(preparedStatement);
                Storage.closeSilently(sqlConnection);
            }
        }

        return players;
    }

    public static List<Player> search(String type, String field, String input) {

        List<Player> players = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.get().getConnection();

            if (type.equals("contains")) {
                preparedStatement = Storage.get().prepare("SELECT * FROM users WHERE " + field + " LIKE ?", sqlConnection);
                preparedStatement.setString(1, "%" + input + "%");
            } else if (type.equals("starts_with")) {
                preparedStatement = Storage.get().prepare("SELECT * FROM users WHERE " + field + " LIKE ?", sqlConnection);
                preparedStatement.setString(1, input + "%");
            } else if (type.equals("ends_with")) {
                preparedStatement = Storage.get().prepare("SELECT * FROM users WHERE " + field + " LIKE ?", sqlConnection);
                preparedStatement.setString(1, "%" + input);
            } else {
                preparedStatement = Storage.get().prepare("SELECT * FROM users WHERE " + field + " = ?", sqlConnection);
                preparedStatement.setString(1, input);
            }

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                players.add(new Player(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("figure"),
                        resultSet.getInt("credits"), resultSet.getInt("duckets"), resultSet.getString("email"),
                        resultSet.getString("mission"), resultSet.getLong("last_online"), resultSet.getInt("rank"),
                        resultSet.getLong("join_date")));
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return players;
    }
}
