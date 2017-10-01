package org.alexdev.icarus.messages.incoming.camera;

import java.io.File;
import java.io.FileOutputStream;

import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class DeletePhotoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        if (!Util.getGameConfig().get("Camera", "camera.enabled", Boolean.class)) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }

        Item item = room.getItemManager().getItem(reader.readInt());

        if (item == null) {
            return;
        }

        room.getMapping().removeItem(item);
        item.delete();

        /*if (Util.getGameConfig().get("Camera", "remove.file.photo.delete", Boolean.class)) {

            final String fileName = item.getExtraData().substring(6).substring(0, item.getExtraData().indexOf(".png") - 2);
            final String filePath = Util.getGameConfig().get("Camera", "camera.path", String.class);
            
            GameScheduler.getScheduler().execute(() -> {
                try {
                    File file = new File(filePath + fileName);
                    
                    if (file.exists()) {
                        file.delete();
                    }
                    
                } catch (Exception e) {
                    Log.exception(e);
                }
            });
        }*/
    }
}
