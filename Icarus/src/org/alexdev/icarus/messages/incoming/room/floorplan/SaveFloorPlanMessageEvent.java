package org.alexdev.icarus.messages.incoming.room.floorplan;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SaveFloorPlanMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasOwnership(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }

        String heightmap = reader.readString().toLowerCase().trim();

        heightmap = heightmap.replace(Character.toString((char)10), "");
        heightmap = heightmap.replace(Character.toString((char)13), "{13}");
        heightmap = heightmap.replace("-", "");

        String[] modelData = heightmap.split("\\{13}");

        int doorX = reader.readInt();
        int doorY = reader.readInt();
        double doorZ = -1;

        try {
            doorZ = RoomModel.parse(modelData[doorY].charAt(doorX));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int doorRotation = reader.readInt();
        int wallThickness = reader.readInt();
        int floorThickness = reader.readInt();
        int wallHeight = reader.readInt();

        RoomModel model = new RoomModel("dynamic_model_" + room.getData().getId(), heightmap, doorX, doorY, (int)doorZ, doorRotation);
        RoomModelDao.newCustomModel(room.getData().getId(), model);

        room.setModel(model);

        room.getData().setWallThickness(wallThickness);
        room.getData().setFloorThickness(floorThickness);
        room.getData().setWallHeight(wallHeight);
        room.getData().setModel("dynamic_model_" + room.getData().getId());
        room.save();

        List<Player> connectedPlayers = new ArrayList<>();

        for (Player user : room.getEntityManager().getPlayers()) {
            connectedPlayers.add(user);
            user.performRoomAction(RoomAction.LEAVE_ROOM, false);
        }

        for (Player user : connectedPlayers) {
            if (user != null) {
                user.performRoomAction(RoomAction.FORWARD_ROOM, room.getData().getId());
            }
        }

    }
}
