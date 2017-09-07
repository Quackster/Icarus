package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;
import org.alexdev.icarus.util.GameSettings;

public class CanCreateRoomMessageComposer extends MessageComposer {

    private Player player;

    public CanCreateRoomMessageComposer(Player player) {
        this.player = player;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.CanCreateRoomMessageComposer);
        this.response.writeInt(this.player.getRooms().size() >= GameSettings.MAX_ROOMS_PER_ACCOUNT ? 1 : 0);
        this.response.writeInt(GameSettings.MAX_ROOMS_PER_ACCOUNT);
    }
}
