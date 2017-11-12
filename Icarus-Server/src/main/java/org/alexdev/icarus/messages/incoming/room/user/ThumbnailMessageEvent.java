package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.dao.mysql.site.SiteDao;
import org.alexdev.icarus.dao.site.SiteKey;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomInfoUpdatedMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.ThumbnailMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;
import org.alexdev.icarus.util.config.Configuration;

import java.io.File;
import java.io.FileOutputStream;

public class ThumbnailMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        boolean createThumbnailsEnabled = GameSettings.THUMBNAIL_ENABLED;
        
        if (!createThumbnailsEnabled) {
            return;
        }
        
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasOwnership(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }
        
        if (room.getData().getThumbnail() != null && room.getData().getThumbnail().length() > 0) {

            final String fileName = room.getData().getThumbnail().split("/")[1];
            final String filePath = GameSettings.THUMBNAIL_PATH;

            GameScheduler.getInstance().getScheduler().execute(() -> {
                try {
                    File file = new File(filePath + fileName);
                    
                    if (file.exists()) {
                        file.delete();
                    }
                    
                } catch (Exception e) {
                	player.getLogger().error("Could not delete thumbnail {} for room id {}", fileName, room.getData().getId(), e);
                }
            });
        }

        String templateFileName = GameSettings.THUMBNAIL_FILENAME;
        String templateFilePath = GameSettings.THUMBNAIL_PATH;
        String templateFileUrl = GameSettings.THUMBNAIL_URL;

        templateFileName = templateFileName.replace("{id}", player.getRoom().getData().getId() + "");
        templateFileName = templateFileName.replace("{generatedId}", Util.generateRandomString(10, false));
        templateFileUrl = templateFileUrl.replace("{filename}", templateFileName);
        
        room.getData().setThumbnail(templateFileUrl);
        room.save();
        
        final int length = reader.readInt();
        final byte[] payload = reader.readBytes(length);
        final String fileName = templateFileName;
        
        GameScheduler.getInstance().getScheduler().execute(() -> {
            try {
                FileOutputStream fos = new FileOutputStream(templateFilePath + fileName);
                fos.write(payload);
                fos.flush();
                fos.close();
            } catch (Exception e) {
            	player.getLogger().error("Could not save thumbnail {} for room id {}", fileName, room.getData().getId(), e);
            }
        });
        
        player.send(new ThumbnailMessageComposer());
        room.send(new RoomInfoUpdatedMessageComposer(room.getData().getId()));

        /*int count = reader.readInt();

        if (!(count > 0)) {
            return;
        }

        byte[] bytes = reader.readBytes(count);
        byte[] buffer = new byte[count * 3];

        try {

            Inflater inflater = new Inflater();
            inflater.setInput(bytes);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while (!inflater.finished()) {
                int amount = inflater.inflate(buffer);
                outputStream.compose(buffer, 0, amount);
            }

            outputStream.close();

            byte[] output = outputStream.toByteArray();
            Log.info(new String(output));

            inflater.end();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
