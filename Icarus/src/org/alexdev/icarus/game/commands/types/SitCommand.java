package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.RoomUser;

public class SitCommand implements Command {

    @Override
    public void handleCommand(Player player, String message) {
        
        RoomUser roomUser = player.getRoomUser();
        
        if (roomUser.getRoom() != null) {
            
            if (roomUser.isWalking()) {
                return;
            }
            
            if (roomUser.containsStatus(EntityStatus.SIT)) {
                return;
            }
            
            int rotation = roomUser.getPosition().getRotation();
            
            if (rotation != 0 && rotation != 2 && rotation != 4 && rotation != 6) {
                return;
            }
            
            roomUser.removeStatus(EntityStatus.DANCE);

            roomUser.setStatus(EntityStatus.SIT, "0");
            roomUser.setNeedUpdate(true);
        }
        
    }

}
