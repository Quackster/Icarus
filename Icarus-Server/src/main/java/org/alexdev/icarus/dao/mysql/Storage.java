package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.alexdev.icarus.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Storage {
    
    private HikariDataSource ds;
    private boolean isConnected;
    
    final private static Logger log = LoggerFactory.getLogger(Storage.class);
    
    public Storage(String host, String username, String password, String db) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + ":3306/" + db + "?useSSL=false");
            config.setUsername(username);
            config.setPassword(password);
            
            config.setPoolName("processing");
            config.setMaximumPoolSize(25);
            config.setMinimumIdle(5);
            
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            this.ds = new HikariDataSource(config);
            this.isConnected = true;

        } catch (Exception ex) {
        	Storage.logError(ex);
        }
    }

    public static void logError(Exception ex) {
    	Log.getErrorLogger().error("Error when executing MySQL query: ", ex);
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
            return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } catch(SQLException e) {
            e.printStackTrace();
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
            Storage.logError(e);
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
            Storage.logError(e);
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
        return this.ds.getHikariPoolMXBean().getActiveConnections();
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {

        try {
            return this.ds.getConnection();
        } catch (SQLException e) {
            Storage.logError(e);
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

	public static Logger getLogger() {
		return log;
	}
}