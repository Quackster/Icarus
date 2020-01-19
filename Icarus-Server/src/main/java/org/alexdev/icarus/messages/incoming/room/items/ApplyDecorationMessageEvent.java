package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ApplyDecorationMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        Item item = player.getInventory().getItem(request.readInt());
        
        if (item == null) {
            return;
        }
        
        Room room = player.getRoomUser().getRoom();
        
        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getEntityId(), true) && !player.hasPermission("room_all_rights")) {
            return;
        }
        
        if (item.getDefinition().getItemName().startsWith("wallpaper")) {
            room.getData().setWall(item.getExtraData());
            room.send(new RoomSpacesMessageComposer("wallpaper", room.getData().getWall()));
        }
        
        if (item.getDefinition().getItemName().startsWith("floor")) {
            room.getData().setFloor(item.getExtraData());
            room.send(new RoomSpacesMessageComposer("floor", room.getData().getFloor()));    
        }

        if (item.getDefinition().getItemName().startsWith("landscape")) {
            room.getData().setLandscape(item.getExtraData());
            room.send(new RoomSpacesMessageComposer("landscape", room.getData().getLandscape()));
        }
        
        room.save();
        item.delete();
        
        player.getInventory().remove(item);
        player.getInventory().updateItems();
    }
}
