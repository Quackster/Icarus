package org.alexdev.icarus.dao.mysql.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.item.interactions.InteractionType;

public class ItemDao {

    /**
     * Gets the furniture.
     *
     * @return the furniture
     */
    public static Map<Integer, ItemDefinition> getFurniture() {
        Map<Integer, ItemDefinition> furni = new HashMap<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM item_definitions", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                
                /*  public ItemDefinition(int id, String publicName, String itemName, String type, int width, int length, double stackHeight,
            boolean canStack, boolean canSit, boolean isRug, int spriteId, boolean allowRecycle,*/

                furni.put(resultSet.getInt("id"), new ItemDefinition(resultSet.getInt("id"), resultSet.getString("public_name"), resultSet.getString("item_name"), resultSet.getString("type"), 
                        resultSet.getInt("width"), resultSet.getInt("length"), resultSet.getDouble("stack_height"), resultSet.getInt("can_stack") == 1,
                        resultSet.getInt("can_sit") == 1, resultSet.getInt("is_walkable") == 1, resultSet.getInt("sprite_id"), resultSet.getInt("allow_recycle") == 1, 
                        resultSet.getInt("allow_trade") == 1, resultSet.getInt("allow_marketplace_sell") == 1, resultSet.getInt("allow_gift") == 1, 
                        resultSet.getInt("allow_inventory_stack") == 1, InteractionType.getType(resultSet.getString("interaction_type")), false, resultSet.getInt("interaction_modes_count"),
                        resultSet.getString("vending_ids"), resultSet.getString("height_adjustable")));
                
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return furni;
    }
    
    /**
     * Gets the item.
     *
     * @param itemId the item id
     * @return the item
     */
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
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return item;
    }

    /**
     * Gets the room items.
     *
     * @param roomId the room id
     * @return the room items
     */
    public static Map<Integer, Item> getRoomItems(int roomId) {
        Map<Integer, Item> items = new HashMap<>();

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
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return items;
    }

    /**
     * Save item.
     *
     * @param item the item
     */
    public static void saveItem(Item item) {
        String x = item.getPosition().getX() + "";
        String y = item.getPosition().getY() + "";

        if (item.getDefinition().getType() == ItemType.WALL) {
            x = item.getSide() + "," + item.getWidthX() + "," + item.getWidthY();
            y = item.getLengthX() + "," + item.getLengthY();
        }
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE items SET extra_data = ?, x = ?, y = ?, z = ?, rotation = ?, room_id = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, item.getExtraData());
            preparedStatement.setString(2, x);
            preparedStatement.setString(3, y);
            preparedStatement.setDouble(4, item.getPosition().getZ());
            preparedStatement.setInt(5, item.getPosition().getRotation());
            preparedStatement.setInt(6, item.getRoomId());
            preparedStatement.setLong(7, item.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
    /**
     * Save item.
     *
     * @param item the item
     */
    public static void saveItemData(Item item) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE items SET extra_data = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, item.getExtraData());
            preparedStatement.setLong(2, item.getId());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Delete item.
     *
     * @param id the id
     */
    public static void deleteItem(long id) {
        Dao.getStorage().execute("DELETE FROM items WHERE id = " + id);
    }

    /**
     * Fill.
     *
     * @param row the row
     * @return the item
     * @throws Exception the exception
     */
    public static Item fill(ResultSet row) throws Exception {
        Item item = new Item(row.getInt("id"), row.getInt("user_id"), row.getInt("group_id"), row.getInt("item_id"), row.getInt("room_id"), row.getString("x"), row.getString("y"), row.getDouble("z"), row.getInt("rotation"), row.getString("extra_data"));
        return item;
    }
}
