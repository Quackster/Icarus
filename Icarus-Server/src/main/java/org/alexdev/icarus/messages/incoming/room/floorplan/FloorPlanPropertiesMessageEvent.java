package org.alexdev.icarus.messages.incoming.room.floorplan;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.floorplan.FloorPlanFloorMapComposer;
import org.alexdev.icarus.messages.outgoing.room.floorplan.FloorPlanVisualMessagesComposer;
import org.alexdev.icarus.messages.outgoing.room.floorplan.FloorPlanDoorMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class FloorPlanPropertiesMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        player.send(new FloorPlanFloorMapComposer(room.getItemManager().getFloorItems()));
        player.send(new FloorPlanDoorMessageComposer(room.getModel().getDoorLocation().getX(), room.getModel().getDoorLocation().getY(), room.getModel().getDoorLocation().getRotation()));
        player.send(new FloorPlanVisualMessagesComposer(room.getData().getWallThickness(), room.getData().getFloorThickness(), room.getData().hasHiddenWall()));
    }
}
