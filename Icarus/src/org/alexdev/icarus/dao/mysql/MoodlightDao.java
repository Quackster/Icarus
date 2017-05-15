package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.moodlight.MoodlightData;
import org.alexdev.icarus.log.Log;

public class MoodlightDao {

    public static boolean hasMoodlightData(int roomId) {

        boolean exists = false;
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT item_id FROM room_items_moodlight WHERE item_id = " + roomId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                exists = true;
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return exists;
    }
    
    public static MoodlightData getMoodlightData(int itemId) {

    	MoodlightData data = null;
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM room_items_moodlight WHERE item_id = " + itemId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data = new MoodlightData(resultSet.getInt("item_id"), resultSet.getInt("current_preset"), resultSet.getInt("enabled") == 1, resultSet.getString("preset_one"), resultSet.getString("preset_three"), resultSet.getString("preset_three"));
            	
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return data;
    }

    public static void saveMoodlightData(MoodlightData data) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE room_items_moodlight SET enabled = ?, current_preset = ?, preset_one = ?, preset_two = ?, preset_three = ? WHERE item_id = ?", sqlConnection);
            preparedStatement.setInt(1, data.isEnabled() ? 1 : 0);
            preparedStatement.setInt(2, data.getCurrentPreset());
            preparedStatement.setString(3, data.getPresets().get(0).toString());
            preparedStatement.setString(4, data.getPresets().get(1).toString());
            preparedStatement.setString(5, data.getPresets().get(2).toString());
            preparedStatement.setInt(6, data.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    
    public static void newMoodlightData(int itemId) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO room_items_moodlight (item_id, enabled, current_preset, preset_one, preset_two, preset_three) VALUES (?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setInt(1, itemId);
            preparedStatement.setInt(2, 0);
            preparedStatement.setInt(3, 1);
            preparedStatement.setString(4, "#000000,255,0");
            preparedStatement.setString(5, "#000000,255,0");
            preparedStatement.setString(6, "#000000,255,0");
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
    public static void deleteMoodlightData(int itemId) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("DELETE FROM room_items_moodlight WHERE item_id = ?", sqlConnection);
            preparedStatement.setInt(1, itemId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static void deleteItem(long id) {
        Dao.getStorage().execute("DELETE FROM items WHERE id = " + id);
    }


    public static Item fill(ResultSet row) throws Exception {
        Item item = new Item(row.getLong("id"), row.getInt("user_id"), row.getInt("item_id"), row.getInt("room_id"), row.getString("x"), row.getString("y"), row.getDouble("z"), row.getInt("rotation"), row.getString("extra_data"));
        return item;
    }

}
