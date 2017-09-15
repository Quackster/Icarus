package org.alexdev.icarus.dao.mysql.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Maps;

public class InventoryDao {
    
    public static Map<Integer, Item> getInventoryItems(int userId) {

        Map<Integer, Item> items = Maps.newHashMap();
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            
            preparedStatement = Dao.getStorage().prepare("SELECT id, user_id, item_id, room_id, x, y, z, rotation, extra_data FROM item_data WHERE room_id = 0 AND user_id = ?", sqlConnection);
            preparedStatement.setInt(1, userId);
            
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                items.put(resultSet.getInt("id"), fill(resultSet));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return items;
    }

    public static Item getItem(long id) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Item item = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            
            preparedStatement = Dao.getStorage().prepare("SELECT id, user_id, item_id, room_id, x, y, z, rotation, extra_data FROM item_data WHERE id = ? LIMIT 1", sqlConnection);
            preparedStatement.setLong(1, id);
            
            resultSet = preparedStatement.executeQuery();
                
            if (resultSet.next()) {
                item = fill(resultSet);
            }
            
        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return item;
    }

    public static Item newItem(int itemId, int ownerId, String extraData) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Item item = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO item_data (owner_id, user_id, item_id, extra_data) VALUES(?, ?, ?, ?)", sqlConnection);
            
            preparedStatement.setInt(1, ownerId);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.setInt(3, itemId);
            preparedStatement.setString(4, extraData);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet != null && resultSet.next()) {
                int inventoryItemId = resultSet.getInt(1);
                return new Item(inventoryItemId, ownerId, itemId, 0, "", "", 0, 0, "");
            }

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return item;
    }
    
    public static Map<Integer, Pet> getInventoryPets(int id) {

        Map<Integer, Pet> items = Maps.newHashMap();
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM pet_data WHERE room_id = 0 AND owner_id = ?", sqlConnection);
            preparedStatement.setInt(1, id);
            
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                items.put(resultSet.getInt("id"), PetDao.fill(resultSet));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return items;
    }
    
    public static Item fill(ResultSet row) throws Exception {
        
        Item item = new Item(row.getInt("id"), row.getInt("user_id"), row.getInt("item_id"), row.getInt("room_id"), row.getString("x"), row.getString("y"), row.getDouble("z"), row.getInt("rotation"), row.getString("extra_data"));
        
        if (item.getDefinition().getInteractionType() == InteractionType.TELEPORT) {
            item.setTeleporterId(TeleporterDao.getPairId(item.getId()));
        }
        
        return item;
    }



}
