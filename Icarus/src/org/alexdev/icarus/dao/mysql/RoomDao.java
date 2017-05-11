package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.alexdev.icarus.factories.RoomFactory;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RoomDao {

    private static HashMap<String, RoomModel> roomModels;

    public static void load() {

        roomModels = Maps.newHashMap();

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
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static List<Room> getPublicRooms(boolean storeInMemory) {

        List<Room> rooms = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE room_type = " + RoomType.PUBLIC.getTypeCode(), sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                Room room = RoomManager.find(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                rooms.add(room);

                if (storeInMemory) {
                    RoomManager.add(room);
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

    public static List<Room> getPlayerRooms(PlayerDetails details, boolean storeInMemory) {

        List<Room> rooms = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE owner_id = " + details.getId(), sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");

                Room room = RoomManager.find(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                rooms.add(room);

                if (storeInMemory) {
                    RoomManager.add(room);
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

    public static Room getRoom(int roomId, boolean storeInMemory) {

        Room room = null;
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM rooms WHERE id = " + roomId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                int id = resultSet.getInt("id");

                room = RoomManager.find(id);

                if (room == null) {
                    room = fill(resultSet);
                }

                if (storeInMemory) {
                    RoomManager.add(room);
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

    public static List<Integer> getRoomRights(int roomId) {

        List<Integer> rooms = Lists.newArrayList();

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("SELECT * FROM room_rights WHERE room_id = " + roomId, sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                rooms.add(resultSet.getInt("user_id"));
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

    public static Room createRoom(Player player, String name, String description, String model, int category, int usersMax, int tradeState) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO rooms (name, description, owner_id, model, category, users_max, trade_state) VALUES (?, ?, ?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, player.getDetails().getId());
            preparedStatement.setString(4, model);
            preparedStatement.setInt(5, category);
            preparedStatement.setInt(6, usersMax);
            preparedStatement.setInt(7, tradeState);
            preparedStatement.executeUpdate();

            ResultSet row = preparedStatement.getGeneratedKeys();

            if (row != null && row.next()) {
                return getRoom(row.getInt(1), true);
            }

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

        return null;
    }

    public static void deleteRoom(Room room) {
        Dao.getStorage().execute("DELETE FROM rooms WHERE id = " + room.getData().getId());
    }

    public static void updateRoom(Room room) {

        RoomData data = room.getData();


        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("UPDATE rooms SET name = ?, description = ?, "
                    + "state = ?, password = ?, users_max = ?, category = ?, tags = ?, trade_state = ?, allow_pets = ?, allow_pets_eat = ?, " 
                    + "allow_walkthrough = ?, hidewall = ?, wall_thickness = ?, floor_thickness = ?, who_can_mute = ?, who_can_kick = ?, who_can_ban = ?, "
                    + "chat_mode = ?, chat_size = ?, chat_speed = ?, chat_distance = ?, chat_flood = ?, wallpaper = ?, floor = ?, outside = ? WHERE id = ?", sqlConnection);

            preparedStatement.setString(1, data.getName());
            preparedStatement.setString(2, data.getDescription());
            preparedStatement.setInt(3, data.getState().getStateCode());
            preparedStatement.setString(4, data.getPassword());
            preparedStatement.setInt(5, data.getUsersMax());
            preparedStatement.setInt(6, data.getCategory());
            preparedStatement.setString(7, String.join(",", data.getTags()));
            preparedStatement.setInt(8, data.getTradeState());
            preparedStatement.setInt(9, data.isAllowPets() ? 1 : 0);
            preparedStatement.setInt(10, data.isAllowPetsEat() ? 1 : 0);
            preparedStatement.setInt(11, data.isAllowWalkthrough() ? 1 : 0);
            preparedStatement.setInt(12, data.isHideWall() ? 1 : 0);
            preparedStatement.setInt(13, data.getWallThickness());
            preparedStatement.setInt(14, data.getFloorThickness());
            preparedStatement.setInt(15, data.getWhoCanMute());
            preparedStatement.setInt(16, data.getWhoCanKick());
            preparedStatement.setInt(17, data.getWhoCanBan());
            preparedStatement.setInt(18, data.getChatType());
            preparedStatement.setInt(19, data.getChatBalloon());
            preparedStatement.setInt(20, data.getChatSpeed());
            preparedStatement.setInt(21, data.getChatMaxDistance());
            preparedStatement.setInt(22, data.getChatFloodProtection());
            preparedStatement.setString(23, data.getWall());
            preparedStatement.setString(24, data.getFloor());
            preparedStatement.setString(25, data.getLandscape());
            preparedStatement.setInt(26, data.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }

    }

    public static RoomModel getModel(String model) {
        return roomModels.get(model);
    }

    public static Room fill(ResultSet row) throws SQLException {

        RoomType type = RoomType.getType(row.getInt("room_type"));

        PlayerDetails details = null;

        if (type == RoomType.PRIVATE) {
            details = PlayerDao.getDetails(row.getInt("owner_id"));
        }

        Room instance = RoomFactory.newRoom();

        instance.getData().fill(row.getInt("id"), type, details == null ? 0 : details.getId(), details == null ? "" : details.getUsername(), row.getString("name"), 
                row.getInt("state"), row.getString("password"), row.getInt("users_now"),
                row.getInt("users_max"), row.getString("description"), row.getInt("trade_state"), row.getInt("score"), row.getInt("category"), 
                row.getInt("category"), row.getString("model"), row.getString("wallpaper"), row.getString("floor"), row.getString("outside"), 
                row.getBoolean("allow_pets"), row.getBoolean("allow_pets_eat"), row.getBoolean("allow_walkthrough"), row.getBoolean("hidewall"), 
                row.getInt("wall_thickness"), row.getInt("floor_thickness"), row.getString("tags"), row.getInt("chat_mode"), row.getInt("chat_size"), row.getInt("chat_speed"),
                row.getInt("chat_distance"), row.getInt("chat_flood"), row.getInt("who_can_mute"), row.getInt("who_can_kick"), row.getInt("who_can_ban"), row.getString("thumbnail"));

        return instance;
    }



}
