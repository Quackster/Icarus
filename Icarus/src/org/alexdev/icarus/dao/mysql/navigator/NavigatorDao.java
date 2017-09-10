package org.alexdev.icarus.dao.mysql.navigator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.navigator.NavigatorCategory;
import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;

public class NavigatorDao {

    public static Room createRoom(Player player, String name, String description, String model, int category, int usersMax, int tradeState) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO rooms (name, description, owner_id, model, category, users_max, trade_state) VALUES (?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, player.getDetails().getID());
            preparedStatement.setString(4, model);
            preparedStatement.setInt(5, category);
            preparedStatement.setInt(6, usersMax);
            preparedStatement.setInt(7, tradeState);
            preparedStatement.executeUpdate();

            ResultSet row = preparedStatement.getGeneratedKeys();

            if (row != null && row.next()) {
                return RoomDao.getRoom(row.getInt(1), true);
            }

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return null;
    }
    
    public static List<NavigatorTab> getTabs(int childID) {

        List<NavigatorTab> tabs = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM navigator_tabs WHERE child_id = " + childID, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                NavigatorTab tab = fill(resultSet);

                tabs.add(tab);
                tabs.addAll(getTabs(tab.getID()));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return tabs;
    }

    public static List<NavigatorCategory> getCategories() {

        List<NavigatorCategory> categories = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM navigator_categories", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(new NavigatorCategory(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getInt("min_rank")));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return categories;
    }

    public static NavigatorTab fill(ResultSet set) throws SQLException {

        NavigatorTab instance = new NavigatorTab();

        instance.fill(set.getInt("id"), set.getInt("child_id"), set.getString("tab_name"), set.getString("title"), set.getByte("button_type"), 
                set.getByte("closed") == 1, set.getByte("thumbnail") == 1, set.getString("room_populator"));

        return instance;
    }
}
