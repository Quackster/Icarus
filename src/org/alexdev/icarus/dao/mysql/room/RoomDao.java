package org.alexdev.icarus.dao.mysql.room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.enums.RoomType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

public class RoomDao {
    
    /**
     * Gets the public rooms.
     *
     * @param storeInMemory the store in memory
     * @return the public rooms
     */
    public static List<Room> getPublicRooms(boolean storeInMemory) {

        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE room_type = ?", sqlConnection);
            preparedStatement.setString(1, RoomType.PUBLIC.name());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                Room room = RoomManager.getByRoomId(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                rooms.add(room);

                if (storeInMemory) {
                    RoomManager.addRoom(room);
                }
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    /**
     * Gets the player rooms.
     *
     * @param userId the user id
     * @param storeInMemory the store in memory
     * @return the player rooms
     */
    public static List<Room> getPlayerRooms(int userId, boolean storeInMemory) {

        List<Room> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE owner_id = " + userId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                Room room = RoomManager.getByRoomId(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                rooms.add(room);

                if (storeInMemory) {
                    RoomManager.addRoom(room);
                }
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return rooms;
    }

    /**
     * Gets the room.
     *
     * @param roomId the room id
     * @param storeInMemory the store in memory
     * @return the room
     */
    public static Room getRoom(int roomId, boolean storeInMemory) {

        Room room = null;
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE id = " + roomId + " LIMIT 1", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("id");

                room = RoomManager.getByRoomId(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                if (storeInMemory) {
                    RoomManager.addRoom(room);
                }
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return room;
    }

    /**
     * Gets the room rights.
     *
     * @param roomId the room id
     * @return the room rights
     */
    public static List<Integer> getRoomRights(int roomId) {

        List<Integer> rooms = new ArrayList<>();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM room_rights WHERE room_id = " + roomId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rooms.add(Integer.valueOf(resultSet.getInt("user_id")));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }


        return rooms;
    }

    /**
     * Clear room rights.
     *
     * @param roomId the room id
     */
    public static void clearRoomRights(int roomId) {
        Dao.getStorage().execute("DELETE FROM room_rights WHERE room_id = '" + roomId + "'");
    }

    /**
     * Removes the room rights.
     *
     * @param roomId the room id
     * @param userId the user id
     */
    public static void removeRoomRights(int roomId, int userId) {
        Dao.getStorage().execute("DELETE FROM room_rights WHERE room_id = '" + roomId + "' AND user_id = '" + userId + "'");
    }

    /**
     * Adds the room rights.
     *
     * @param roomId the room id
     * @param userId the user id
     */
    public static void addRoomRights(int roomId, int userId) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("INSERT INTO room_rights (room_id, user_id) VALUES (?, ?)", sqlConnection);
            preparedStatement.setInt(1, roomId);
            preparedStatement.setInt(2, userId);
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
     * Delete room.
     *
     * @param roomId the room id
     */
    public static void deleteRoom(int roomId) {
        Dao.getStorage().execute("DELETE FROM rooms WHERE id = " + roomId);
    }

    /**
     * Update room.
     *
     * @param room the room
     */
    public static void update(Room room) {

        RoomData data = room.getData();
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("UPDATE rooms SET name = ?, description = ?, "
                    + "state = ?, password = ?, users_max = ?, category = ?, tags = ?, trade_state = ?, allow_pets = ?, allow_pets_eat = ?, " 
                    + "allow_walkthrough = ?, hidewall = ?, wall_thickness = ?, floor_thickness = ?, who_can_mute = ?, who_can_kick = ?, who_can_ban = ?, "
                    + "chat_mode = ?, chat_size = ?, chat_speed = ?, chat_distance = ?, chat_flood = ?, wallpaper = ?, floor = ?, outside = ?, model = ?, group_id = ?, thumbnail = ? WHERE id = ? LIMIT 1", sqlConnection);

            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, data.getDescription());
            preparedStatement.setString(3, data.getState().name());
            preparedStatement.setString(4, data.getPassword());
            preparedStatement.setInt(5, data.getUsersMax());
            preparedStatement.setInt(6, data.getCategory());
            preparedStatement.setString(7, String.join(",", data.getTags()));
            preparedStatement.setInt(8, data.getTradeState());
            preparedStatement.setInt(9, data.isAllowPets() ? 1 : 0);
            preparedStatement.setInt(10, data.isAllowPetsEat() ? 1 : 0);
            preparedStatement.setInt(11, data.isAllowWalkthrough() ? 1 : 0);
            preparedStatement.setInt(12, data.hasHiddenWall() ? 1 : 0);
            preparedStatement.setInt(13, data.getWallThickness());
            preparedStatement.setInt(14, data.getFloorThickness());
            preparedStatement.setInt(15, data.getWhoCanMute());
            preparedStatement.setInt(16, data.getWhoCanKick());
            preparedStatement.setInt(17, data.getWhoCanBan());
            preparedStatement.setInt(18, data.getBubbleMode());
            preparedStatement.setInt(19, data.getBubbleType());
            preparedStatement.setInt(20, data.getBubbleScroll());
            preparedStatement.setInt(21, data.getChatMaxDistance());
            preparedStatement.setInt(22, data.getChatFloodProtection());
            preparedStatement.setString(23, data.getWall());
            preparedStatement.setString(24, data.getFloor());
            preparedStatement.setString(25, data.getLandscape());
            preparedStatement.setString(26, data.getModel());
            preparedStatement.setInt(27, data.getGroupId());
            preparedStatement.setString(28, data.getThumbnail());
            preparedStatement.setInt(29, data.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Save chatlog.
     *
     * @param chatter the chatter
     * @param roomId the room id
     * @param chatType the chat type
     * @param message the message
     */
    public static void saveChatlog(Player chatter, int roomId, String chatType, String message) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO room_chatlogs (user, room_id, timestamp, message_type, message) VALUES (?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, chatter.getDetails().getName());
            preparedStatement.setInt(2, roomId);
            preparedStatement.setLong(3, Util.getCurrentTimeSeconds());

            if (chatType.equals("CHAT")) {
                preparedStatement.setInt(4, 0);
            } else if (chatType.equals("SHOUT")) {
                preparedStatement.setInt(4, 1);
            } else {
                preparedStatement.setInt(4, 2);
            }

            preparedStatement.setString(5, message);
            preparedStatement.execute();

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    /**
     * Fill.
     *
     * @param row the row
     * @return the room
     * @throws SQLException the SQL exception
     */
    public static Room fill(ResultSet row) throws SQLException {

        RoomType type = RoomType.valueOf(row.getString("room_type"));

        String ownerName = "";

        if (type == RoomType.PRIVATE) {
            ownerName = PlayerDao.getName(row.getInt("owner_id"));
        }

        RoomData data = new RoomData(row.getInt("id"), type, row.getInt("owner_id"), ownerName, row.getString("name"), 
                row.getString("state"), row.getString("password"), row.getInt("users_now"),
                row.getInt("users_max"), row.getString("description"), row.getInt("trade_state"), row.getInt("score"), row.getInt("category"), 
                row.getInt("group_id"), row.getString("model"), row.getString("wallpaper"), row.getString("floor"), row.getString("outside"), 
                row.getBoolean("allow_pets"), row.getBoolean("allow_pets_eat"), row.getBoolean("allow_walkthrough"), row.getBoolean("hidewall"), 
                row.getInt("wall_thickness"), row.getInt("floor_thickness"), row.getString("tags"), row.getInt("chat_mode"), row.getInt("chat_size"), row.getInt("chat_speed"),
                row.getInt("chat_distance"), row.getInt("chat_flood"), row.getInt("who_can_mute"), row.getInt("who_can_kick"), row.getInt("who_can_ban"), row.getString("thumbnail"));
        
        Room room = new Room(data);
        return room;
    }
}
