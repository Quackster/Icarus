package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.log.Log;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class Storage {

    private BoneCP connections = null;
    private BoneCPConfig config;
    private boolean isConnected = false;

    public Storage(String host, String username, String password, String db) {

        try {

            config = new BoneCPConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + "/" + db);
            config.setUsername(username);
            config.setPassword(password);

            config.setMinConnectionsPerPartition(0);
            config.setMaxConnectionsPerPartition(5);
            config.setConnectionTimeout(1000, TimeUnit.SECONDS);
            config.setPartitionCount(Runtime.getRuntime().availableProcessors()); // set partion count to number of cores (inc. hyperthreading)

            this.connections = new BoneCP(config);
            this.isConnected = true;

        } catch (Exception ex) {
            this.isConnected = false;
            Log.exception(ex);
        }
    }

    /**
     * Prepare.
     *
     * @param query the query
     * @param conn the conn
     * @return the prepared statement
     * @throws SQLException the SQL exception
     */
    public PreparedStatement prepare(String query, Connection conn) throws SQLException {

        try {
            conn = this.connections.getConnection();
            return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

        return null;
    }
    
    /**
     * Execute.
     *
     * @param query the query
     */
    public void execute(String query) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = this.getConnection();
            preparedStatement = this.prepare(query, sqlConnection);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
    /**
     * Gets the string.
     *
     * @param query the query
     * @return the string
     */
    public String getString(String query) {
        
        String value = null;
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = this.getConnection();
            preparedStatement = this.prepare(query, sqlConnection);
            
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            
            value = resultSet.getString(query.split(" ")[1]);

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return value;
    }
    
    /**
     * Check driver.
     */
    public void checkDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection count.
     *
     * @return the connection count
     */
    public int getConnectionCount() {
        return this.connections.getTotalLeased();
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {

        try {
            return this.connections.getConnection();
        } catch (SQLException e) {
            Log.exception(e);
        }

        return null;
    }

    /**
     * Checks if is connected.
     *
     * @return true, if is connected
     */
    public boolean isConnected() {
        return this.isConnected;
    }

    /**
     * Close silently.
     *
     * @param resultSet the result set
     */
    public static void closeSilently(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) { }
        
    }

    /**
     * Close silently.
     *
     * @param preparedStatement the prepared statement
     */
    public static void closeSilently(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (Exception e) { }
        
    }

    /**
     * Close silently.
     *
     * @param sqlConnection the sql connection
     */
    public static void closeSilently(Connection sqlConnection) {
        try {
            sqlConnection.close();
        } catch (Exception e) { }
        
    }
}