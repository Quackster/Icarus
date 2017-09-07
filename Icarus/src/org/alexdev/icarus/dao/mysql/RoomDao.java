package org.alexdev.icarus.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RoomDao {

    private static HashMap<String, RoomModel> roomModels;

    public static void getModels() {

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
            try {
                Log.println("Error with model: " + resultSet.getString("id"));
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
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
                    + "chat_mode = ?, chat_size = ?, chat_speed = ?, chat_distance = ?, chat_flood = ?, wallpaper = ?, floor = ?, outside = ?, model = ? WHERE id = ?", sqlConnection);

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
            preparedStatement.setInt(18, data.getChatMode());
            preparedStatement.setInt(19, data.getChatSize());
            preparedStatement.setInt(20, data.getChatSpeed());
            preparedStatement.setInt(21, data.getChatMaxDistance());
            preparedStatement.setInt(22, data.getChatFloodProtection());
            preparedStatement.setString(23, data.getWall());
            preparedStatement.setString(24, data.getFloor());
            preparedStatement.setString(25, data.getLandscape());
            preparedStatement.setString(26, data.getModel());
            preparedStatement.setInt(27, data.getId());
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
    
    public static void saveChatlog(Player chatter, int roomID, String chatType, String message) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO room_chatlogs (user, room_id, timestamp, message_type, message) VALUES (?, ?, ?, ?, ?)", sqlConnection);
            preparedStatement.setString(1, chatter.getDetails().getName());
            preparedStatement.setInt(2, roomID);
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
                Log.println("Error with model: " + resultSet.getString("id"));
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return model;
    }
    
    public static void newCustomModel(int roomId, RoomModel model) {

        deleteCustomModel(roomId);
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
    
    public static void deleteCustomModel(int roomId) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();
            preparedStatement = Dao.getStorage().prepare("DELETE FROM room_models_dynamic WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, "dynamic_model_" + roomId);
            preparedStatement.execute();
            
        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }

    public static Room fill(ResultSet row) throws SQLException {

        RoomType type = RoomType.getType(row.getInt("room_type"));

        String ownerName = "";

        if (type == RoomType.PRIVATE) {
            ownerName = PlayerDao.getName(row.getInt("owner_id"));
        }

        Room instance = new Room();

        instance.getData().fill(row.getInt("id"), type, row.getInt("owner_id"), ownerName, row.getString("name"), 
                row.getInt("state"), row.getString("password"), row.getInt("users_now"),
                row.getInt("users_max"), row.getString("description"), row.getInt("trade_state"), row.getInt("score"), row.getInt("category"), 
                row.getInt("category"), row.getString("model"), row.getString("wallpaper"), row.getString("floor"), row.getString("outside"), 
                row.getBoolean("allow_pets"), row.getBoolean("allow_pets_eat"), row.getBoolean("allow_walkthrough"), row.getBoolean("hidewall"), 
                row.getInt("wall_thickness"), row.getInt("floor_thickness"), row.getString("tags"), row.getInt("chat_mode"), row.getInt("chat_size"), row.getInt("chat_speed"),
                row.getInt("chat_distance"), row.getInt("chat_flood"), row.getInt("who_can_mute"), row.getInt("who_can_kick"), row.getInt("who_can_ban"), row.getString("thumbnail"));

        return instance;
    }



}
