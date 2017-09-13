package org.alexdev.icarus.messages.incoming.groups;

import java.util.List;
import java.util.stream.Collectors;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.GroupPurchaseDialogComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GroupCatalogueMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        List<Room> roomsWithoutGroup = player.getRooms().stream()
                .filter(room -> room.getGroup() == null)
                .collect(Collectors.toList());
        
        player.send(new GroupPurchaseDialogComposer(roomsWithoutGroup));
    }
}
