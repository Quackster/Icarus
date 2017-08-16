package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.alexdev.icarus.log.Log;

public class ClubDao {

    public static long[] getSubscription(int userId) {

        long[] subscriptionData = null;

            Connection sqlConnection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            try {

                sqlConnection = Dao.getStorage().getConnection();
                
                preparedStatement = Dao.getStorage().prepare("SELECT * FROM users_subscriptions WHERE user_id = ? LIMIT 1", sqlConnection);
                preparedStatement.setInt(1, userId);
                
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    subscriptionData = new long[] { resultSet.getLong("expire_time"), resultSet.getLong("bought_time") };
                    
                }

            } catch (Exception e) {
                Log.exception(e);
            } finally {
                Storage.closeSilently(resultSet);
                Storage.closeSilently(preparedStatement);
                Storage.closeSilently(sqlConnection);
            }

        return subscriptionData;
    }
    
    public static void create(int userId, long expireTime, long boughtTime) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO users_subscriptions (user_id, expire_time, bought_time) VALUES (?, ?, ?)", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.setLong(2, expireTime);
            preparedStatement.setLong(3, boughtTime);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
    public static void update(int userId, long expireTime, long boughtTime) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE users_subscriptions SET expire_time = ?, bought_time = ? WHERE user_id = ?", sqlConnection);
            preparedStatement.setLong(1, expireTime);
            preparedStatement.setLong(2, boughtTime);
            preparedStatement.setInt(3, userId);  
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void delete(int userId) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("DELETE FROM users_subscriptions WHERE user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
}
