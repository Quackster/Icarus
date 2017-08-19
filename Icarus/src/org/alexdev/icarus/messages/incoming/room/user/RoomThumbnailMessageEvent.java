package org.alexdev.icarus.messages.incoming.room.user;

import java.io.FileOutputStream;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RoomThumbnailMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        //int count = request.readInt();

        //byte[] bytes = request.readBytes(count);
        //byte[] buffer = new byte[bytes.length * 3];

        try {

            byte[] msg = request.getRawMessage();
     
            FileOutputStream fos = new FileOutputStream("pathname.png");
            fos.write(msg);
            fos.close();


            /*Inflater inflater = new Inflater();
            inflater.setInput(bytes);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while (!inflater.finished()) {
                int amount = inflater.inflate(buffer);
                outputStream.write(buffer, 0, amount);
            }

            outputStream.close();
            byte[] output = outputStream.toByteArray();

            Log.println(new String(output));
            
            inflater.end();
            outputStream.close();*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
