package org.alexdev.icarus.dao.mysql.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Maps;

public class ItemDao {

    public static Map<Integer, ItemDefinition> getFurniture() {

        Map<Integer, ItemDefinition> furni = Maps.newHashMap();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM furniture", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                furni.put(resultSet.getInt("id"), new ItemDefinition(resultSet.getInt("id"), resultSet.getString("public_name"), resultSet.getString("item_name"), resultSet.getString("type"), 
                        resultSet.getInt("width"), resultSet.getInt("length"), resultSet.getDouble("stack_height"), resultSet.getInt("can_stack") == 1,
                        resultSet.getInt("can_sit") == 1, resultSet.getInt("is_walkable") == 1, resultSet.getInt("sprite_id"), resultSet.getInt("allow_recycle") == 1, 
                        resultSet.getInt("allow_trade") == 1, resultSet.getInt("allow_marketplace_sell") == 1, resultSet.getInt("allow_gift") == 1, 
                        resultSet.getInt("allow_inventory_stack") == 1, InteractionType.getType(resultSet.getString("interaction_type")), resultSet.getInt("interaction_modes_count"),
                        resultSet.getString("vending_ids"), resultSet.getString("variable_heights")));
                
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return furni;

    }
    
    public static Item getItem(int itemId) {

        Item item = null;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM items WHERE id = " + itemId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
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

    public static Map<Integer, Item> getRoomItems(int roomId) {

        Map<Integer, Item> items = Maps.newHashMap();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM items WHERE room_id = " + roomId, sqlConnection);
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

    public static void saveItem(Item item) {

        String x = item.getPosition().getX() + "";
        String y = item.getPosition().getY() + "";

        if (item.getType() == ItemType.WALL) {
            x = item.getSide() + "," + item.getWidthX() + "," + item.getWidthY();
            y = item.getLengthX() + "," + item.getLengthY();
        }
        
        String extraData = item.getExtraData();
        
        if (item.getTeleporterId() > 0) {
            extraData = String.valueOf(item.getTeleporterId());
        }

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE items SET extra_data = ?, x = ?, y = ?, z = ?, rotation = ?, room_id = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, extraData);
            preparedStatement.setString(2, x);
            preparedStatement.setString(3, y);
            preparedStatement.setDouble(4, item.getPosition().getZ());
            preparedStatement.setInt(5, item.getPosition().getRotation());
            preparedStatement.setInt(6, item.getRoomId());
            preparedStatement.setLong(7, item.getId());
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
