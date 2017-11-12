package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CanCreateRoomMessageComposer extends MessageComposer {

    private Player player;

    public CanCreateRoomMessageComposer(Player player) {
        this.player = player;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.CanCreateRoomMessageComposer);
        response.writeInt(this.player.getRooms().size() >= GameSettings.MAX_ROOMS_PER_USER ? 1 : 0);
        response.writeInt(GameSettings.MAX_ROOMS_PER_USER);
    }

    @Override
    public short getHeader() {
        return Outgoing.CanCreateRoomMessageComposer;
    }
}