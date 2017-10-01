package org.alexdev.icarus.messages.incoming.room.user;

import java.io.FileOutputStream;

import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomInfoUpdatedMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.ThumbnailMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class ThumbnailMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        boolean createThumbnailsEnabled = Util.getGameConfig().get("Thumbnail", "thumbnail.create.enabled", Boolean.class);
        
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

        String templateFileName = Util.getGameConfig().get("Thumbnail", "thumbnail.filename", String.class);
        String templateFilePath = Util.getGameConfig().get("Thumbnail", "thumbnail.path", String.class);
        String templateFileUrl = Util.getGameConfig().get("Thumbnail", "thumbnail.url", String.class);

        templateFileName = templateFileName.replace("{id}", player.getRoom().getData().getId() + "");
        templateFileName = templateFileName.replace("{generatedId}", Util.generateRandomString(10, false));
        templateFileUrl = templateFileUrl.replace("{filename}", templateFileName);
        
        room.getData().setThumbnail(templateFileUrl);
        room.save();
        
        final int length = reader.readInt();
        final byte[] payload = reader.readBytes(length);
        final String fileName = templateFileName;
        
        GameScheduler.getScheduler().execute(() -> {
            try {
                FileOutputStream fos = new FileOutputStream(templateFilePath + fileName);
                fos.write(payload);
                fos.flush();
                fos.close();
            } catch (Exception e) {
                Log.exception(e);
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
                outputStream.write(buffer, 0, amount);
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
