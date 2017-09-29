package org.alexdev.icarus.messages.incoming.room.user;

import java.io.FileOutputStream;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomInfoUpdatedMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class ThumbnailMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasOwnership(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }

        String templateFileName = Util.getGameConfig().get("Room", "room.thumbnail.filename", String.class);
        String templateFilePath = Util.getGameConfig().get("Room", "room.thumbnail.path", String.class);
        String templateFileUrl = Util.getGameConfig().get("Room", "room.thumbnail.url", String.class);

        templateFileName = templateFileName.replace("{id}", player.getRoom().getData().getId() + "");
        templateFileName = templateFileName.replace("{generatedId}", Util.generateRandomString(10, false));
        templateFileUrl = templateFileUrl.replace("{filename}", templateFileName);
        
        room.getData().setThumbnail(templateFileUrl);
        room.save();
        
        try {
            
            byte[] msg = reader.getRawMessage();  
            
            FileOutputStream fos = new FileOutputStream(templateFilePath + templateFileName);
            fos.write(msg);
            fos.close();

        } catch (Exception e) {
            Log.exception(e);
        }
        
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
