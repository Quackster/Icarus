package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.floorplan.FloorPlanFloorMapComposer;
import org.alexdev.icarus.messages.outgoing.room.floorplan.FloorPlanRoomVisualComposer;
import org.alexdev.icarus.messages.outgoing.room.floorplan.FloorPlanSendDoorComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class FloorPlanPropertiesMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage reader) {
		
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        player.send(new FloorPlanFloorMapComposer(room.getFloorItems()));
        player.send(new FloorPlanSendDoorComposer(room.getModel().getDoorLocation().getX(), room.getModel().getDoorLocation().getY(), room.getModel().getDoorLocation().getRotation()));
        player.send(new FloorPlanRoomVisualComposer(room.getData().getWallThickness(), room.getData().getFloorThickness(), room.getData().isHideWall()));
	}

}
