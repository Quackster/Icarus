package org.alexdev.icarus.dao.mysql.site;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SiteDao {

    public static void updateKey(String key, int value) {
        updateKey(key, String.valueOf(value));
    }

    public static void updateKey(String key, String value) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("UPDATE `site_config` SET `value` = ? WHERE `key` = ?", sqlConnection);
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, key);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}