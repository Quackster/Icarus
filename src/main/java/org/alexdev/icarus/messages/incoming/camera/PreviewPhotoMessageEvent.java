package org.alexdev.icarus.messages.incoming.camera;

import java.io.FileOutputStream;

import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.camera.PhotoPreviewMessageComposer;
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
            player.sendMessage(Util.getLocaleEntry("camera.error"));
            return;
        }

        String fileName = Util.getGameConfig().get("Camera", "camera.filename", String.class);
        String filePath = Util.getGameConfig().get("Camera", "camera.path", String.class);

        fileName = fileName.replace("{username}", player.getDetails().getName());
        fileName = fileName.replace("{id}", room.getData().getId() + "");
        fileName = fileName.replace("{generatedId}", Util.generateRandomString(10, false));
        
        final String newFileName = fileName;

        GameScheduler.getInstance().getScheduler().execute(() -> {

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
        player.send(new PhotoPreviewMessageComposer(fileName));

        /*try {
            byte[] buffer = new byte[4096 * 3];
            reader.readInt();
            byte[] data = reader.readBytes(reader.getLength() - 6);

            Inflater inflater = new Inflater();
            inflater.setInput(data);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.compose(buffer, 0, count);
            }

            outputStream.close();
            inflater.end();

            byte[] output = outputStream.toByteArray();

            PrintWriter writer = new PrintWriter(new File("image.json"));
            writer.compose(new String(output));
            writer.flush();
            writer.close();




        } catch (Exception e) {
            player.getLogger().error("Could not save photo for room id {}", room.getData().getId(), e);
        }*/

    }
}
