package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;

public class ResetDecoration extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }
    
    @Override
    public void addArguments() {
        this.permissions.add("type");
    }
    
    @Override
    public void handleCommand(Player player, String message, String[] args) {

        if (player.getRoom() == null) {
            return;
        }
        
        Room room = player.getRoom();
        
        if (!room.hasOwnership(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }
        
        String type = args[0];
        
        if (type.startsWith("wall")) {
            room.getData().setWall("0");
            room.send(new RoomSpacesMessageComposer("wallpaper", room.getData().getWall()));
            
        } else if (type.startsWith("floor")) {
            room.getData().setFloor("0");
            room.send(new RoomSpacesMessageComposer("floor", room.getData().getFloor()));    
            
        } else if (type.startsWith("landscape")) {
            room.getData().setLandscape("");
            room.send(new RoomSpacesMessageComposer("landscape", room.getData().getLandscape()));
            
        } else {
            player.sendMessage("You did not tell us whether you wanted the floor, wall or landscape (outside) reset.");
        }
        
    }
    

    @Override
    public String getDescription() {
        return "Resets the room decorations by given argument (wall, floor, landscape).";
    }
}
