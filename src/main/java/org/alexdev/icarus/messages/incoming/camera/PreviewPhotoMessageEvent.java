package org.alexdev.icarus.messages.incoming.camera;

import java.io.FileOutputStream;

import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.camera.PhotoPreviewComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class PreviewPhotoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        if (!Util.getGameConfig().get("Camera", "camera.enabled", Boolean.class)) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        final int photoLength = reader.readInt();
        final byte[] photoPayload = reader.readBytes(photoLength);

        if (!new String(photoPayload).contains("PNG")) {
            player.sendMessage(Util.getLocale("camera.error"));
            return;
        }

        String fileName = Util.getGameConfig().get("Camera", "camera.filename", String.class);
        String filePath = Util.getGameConfig().get("Camera", "camera.path", String.class);

        fileName = fileName.replace("{username}", player.getDetails().getName());
        fileName = fileName.replace("{id}", room.getData().getId() + "");
        fileName = fileName.replace("{generatedId}", Util.generateRandomString(10, false));
        
        final String newFileName = fileName;

        GameScheduler.getScheduler().execute(() -> {
            try {
                FileOutputStream fos = new FileOutputStream(filePath + newFileName);
                fos.write(photoPayload);
                fos.flush();
                fos.close();
                
            } catch (Exception e) {
            	player.getLogger().error("Could not save photo {} for room id {}", newFileName, room.getData().getId(), e);
            }
        });

        player.getMetadata().set("latestPhotoUrl", fileName);
        player.send(new PhotoPreviewComposer(fileName));
    }
}