package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class HeightMapMessageComposer extends MessageComposer {

    private RoomModel roomModel;

    public HeightMapMessageComposer(RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    @Override
    public void compose(Response response) {

        String[] map = roomModel.getHeightMap().split("\\{13}");

        //response.init(Outgoing.HeightMapMessageComposer);
        response.writeInt(roomModel.getMapSizeX());
        response.writeInt(roomModel.getMapSizeX() * roomModel.getMapSizeY());

        for (int y = 0; y < roomModel.getMapSizeY(); y++) {

            String line = map[y];
            line = line.replace(Character.toString((char) 10), "");
            line = line.replace(Character.toString((char) 13), "");

            for (char pos : line.toCharArray()) {

                if (pos == 'x') {
                    response.writeShort(-1);
                } else {

                    int height = 0;

                    if (this.tryParseInt(Character.toString(pos))) {
                        height = Integer.valueOf(Character.toString(pos));
                    } else {

                        int intValue = (int) pos;
                        height = (intValue - 87);
                    }

                    response.writeShort(height * 256);
                }
            }
        }
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.HeightMapMessageComposer;
    }
}
