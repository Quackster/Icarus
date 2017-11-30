package org.alexdev.icarus.dao.mysql.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.room.model.RoomModel;

public class RoomModelDao {
    
    /**
     * Gets the models.
     *
     * @return the models
     */
    public static HashMap<String, RoomModel> getModels() {

        HashMap<String, RoomModel> roomModels = new HashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM room_models", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomModels.put(resultSet.getString("id"), new RoomModel(resultSet.getString("id"), resultSet.getString("heightmap"), resultSet.getInt("door_x"), resultSet.getInt("door_y"), resultSet.getInt("door_z"), resultSet.getInt("door_dir")));
            }

        } catch (Exception e) {
                Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return roomModels;
    }
    
    /**
     * Gets the custom model.
     *
     * @param roomId the room id
     * @return the custom model
     */
    public static RoomModel getCustomModel(int roomId) {
        RoomModel model = null;
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM room_models_dynamic WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, "dynamic_model_" + roomId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                model = new RoomModel(resultSet.getString("id"), resultSet.getString("heightmap"), resultSet.getInt("door_x"), resultSet.getInt("door_y"), resultSet.getInt("door_z"), resultSet.getInt("door_dir"));
            }

        } catch (Exception e) {
            try {
                Storage.getLogger().info("Error with loading model {} for room {} ", resultSet.getString("id"), roomId);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return model;
    }

    /**
     * New custom model.
     *
     * @param roomId the room id
     * @param model the model
     */
    public static void newCustomModel(int roomId, RoomModel model) {
        deleteCustomModel(roomId);

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO room_models_dynamic (id, door_x, door_y, door_z, door_dir, heightmap, wall_height) VALUES (?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, "dynamic_model_" + roomId);
            preparedStatement.setInt(2, model.getDoorLocation().getX());
            preparedStatement.setInt(3, model.getDoorLocation().getY());
            preparedStatement.setInt(4, (int)model.getDoorLocation().getZ());
            preparedStatement.setInt(5, model.getDoorLocation().getRotation());
            preparedStatement.setString(6, model.getHeightMap());
            preparedStatement.setInt(7, model.getWallHeight());
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Delete custom model.
     *
     * @param roomId the room id
     */
    public static void deleteCustomModel(int roomId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("DELETE FROM room_models_dynamic WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, "dynamic_model_" + roomId);
            preparedStatement.execute();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
