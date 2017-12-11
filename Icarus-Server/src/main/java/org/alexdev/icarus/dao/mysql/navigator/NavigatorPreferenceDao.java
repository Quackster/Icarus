package org.alexdev.icarus.dao.mysql.navigator;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.navigator.NavigatorPreference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class NavigatorPreferenceDao {

    /**
     * Gets if the key exists
     *
     * @param userId the user id
     * @param navigatorTabId the navigator tab id
     * @param preference the preference setting
     * @return true, if successful
     */
    public static boolean exists(int userId, int navigatorTabId, NavigatorPreference preference) {
        boolean exists = false;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM navigator_preferences WHERE user_id = ? AND tab_id = ? AND setting = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, navigatorTabId);
            preparedStatement.setString(3, preference.name());
            resultSet = preparedStatement.executeQuery();
            exists = resultSet.next();

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
     * Gets the key value
     *
     * @param userId the user id
     * @param navigatorTabId the navigator tab id
     * @param preference the preference setting
     * @return true, if successful
     */
    public static boolean get(int userId, int navigatorTabId, NavigatorPreference preference) {
        /*if (!NavigatorPreferenceDao.exists(userId, navigatorTabId, preference)) {
            NavigatorPreferenceDao.create(userId, navigatorTabId, preference, defaultValue);
        }*/

        String value = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM navigator_preferences WHERE user_id = ? AND tab_id = ? AND setting = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, navigatorTabId);
            preparedStatement.setString(3, preference.name());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                value = resultSet.getString("value");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return getBoolean(value);
    }

    /**
     * Update the key value
     *
     * @param userId the user id
     * @param navigatorTabId the navigator tab id
     * @param preference the preference setting
     * @param value the default value to insert or the value to override
     * @return true, if successful
     */
    public static boolean update(int userId, int navigatorTabId, NavigatorPreference preference, boolean value) {
        /*if (!NavigatorPreferenceDao.exists(userId, navigatorTabId, preference)) {
            NavigatorPreferenceDao.create(userId, navigatorTabId, preference, value);
            return true;
        }*/

        boolean success = false;
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE navigator_preferences SET value = ? WHERE user_id = ? AND tab_id = ? AND setting = ?", sqlConnection);
            preparedStatement.setBoolean(1, value);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, navigatorTabId);
            preparedStatement.setString(4, preference.name());
            preparedStatement.execute();
            success = true;

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return success;
    }

    /**
     * Create the navigator preference
     *
     * @param userId the user id
     * @param navigatorTabId the navigator tab id
     * @param preference the preference setting
     * @param defaultValue the default value to insert
     */
    public static void create(int userId, int navigatorTabId, NavigatorPreference preference, boolean defaultValue) {
        if (NavigatorPreferenceDao.exists(userId, navigatorTabId, preference)) {
            return;
        }

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO navigator_preferences (user_id, tab_id, value, setting) VALUES (?, ?, ?, ?)", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, navigatorTabId);
            preparedStatement.setBoolean(3, defaultValue);
            preparedStatement.setString(4, preference.name());
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Gets the key value by boolean
     *
     * @param value the value to parse
     * @return true, if successful
     */
    public static boolean getBoolean(String value) {
        if (value == null) {
            return false;
        }

        return value.equals("1") || value.equals("yes") || value.equals("true") || value.equals("on");
    }
}
