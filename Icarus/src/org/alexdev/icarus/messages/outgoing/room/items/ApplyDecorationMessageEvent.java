package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ApplyDecorationMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        int itemId = request.readInt();
        
        Item item = player.getInventory().getItem(itemId);
        
        if (item == null) {
            return;
        }
        
        Room room = player.getRoomUser().getRoom();
        
        if (room == null) {
            return;
        }
        
        if (item.getDefinition().getItemName().startsWith("wallpaper")) {
            room.getData().setWall(item.getExtraData());
            room.send(new RoomSpacesMessageComposer("wallpaper", room.getData().getWall()));
        }
        
        if (item.getDefinition().getItemName().startsWith("a2")) {
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
        player.getInventory().update();
    }

}
