package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class HeightMapMessageComposer extends MessageComposer {
    private Room room;

    public HeightMapMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void compose(Response response) {
        try {
            //response.init(Outgoing.HeightMapMessageComposer);
            response.writeInt(this.room.getModel().getMapSizeX());
            response.writeInt(this.room.getModel().getMapSizeX() * this.room.getModel().getMapSizeY());

            for (int y = 0; y < this.room.getModel().getMapSizeY(); y++) {
                for (int x = 0; x < this.room.getModel().getMapSizeX(); x++) {
                    var roomTile = this.room.getMapping().getTile(x, y);

                    if (roomTile == null) {
                        response.writeShort(Short.MAX_VALUE);
                    } else {
                        response.writeShort(roomTile.getRelativeHeight());
                    }
                }
            }

             /*   String line = map[y];
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
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
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
