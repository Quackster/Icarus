package org.alexdev.icarus.dao.mysql.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;


public class TeleporterDao {

    /**
     * Gets the pair id.
     *
     * @param id the id
     * @return the pair id
     */
    public static int getPairId(int id) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("SELECT * FROM item_teleporter_links WHERE item_one = ? OR item_two = ? LIMIT 1;", sqlConnection);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                if (resultSet.getInt("item_two") != id) {
                    return resultSet.getInt("item_two");
                } else {
                    return resultSet.getInt("item_one");
                }
                
            }
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Save pair.
     *
     * @param item1 the item 1
     * @param item2 the item 2
     */
    public static void savePair(int item1, int item2) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT into item_teleporter_links (item_one, item_two) VALUES(?, ?);", sqlConnection);
            preparedStatement.setInt(1, item1);
            preparedStatement.setInt(2, item2);
            preparedStatement.execute();
            
        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

    }
}