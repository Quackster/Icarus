package org.alexdev.icarus.messages.incoming.camera;

import org.alexdev.icarus.dao.mysql.site.SiteDao;
import org.alexdev.icarus.dao.site.SiteKey;
import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.config.Configuration;

public class DeletePhotoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        if (!GameSettings.CAMERA_ENABLED) {
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

        /*if (Configuration.getInstance().getServerConfig().getGameConfig().get("Camera", "remove.file.photo.delete", Boolean.class)) {

            final String fileName = item.getExtraData().substring(6).substring(0, item.getExtraData().indexOf(".png") - 2);
            final String filePath = Configuration.getInstance().getServerConfig().getGameConfig().get("Camera", "camera.path", String.class);
            
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
