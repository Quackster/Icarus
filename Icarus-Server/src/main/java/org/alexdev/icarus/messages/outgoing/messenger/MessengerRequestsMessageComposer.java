package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MessengerRequestsMessageComposer extends MessageComposer {

    private Player player;
    private List<MessengerUser> requests;

    public MessengerRequestsMessageComposer(Player player, List<MessengerUser> requests) {
        this.player = player;
        this.requests = requests;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.MessengerRequestsMessageComposer);
        response.writeInt(this.player.getEntityId());
        response.writeInt(this.requests.size());

        for (MessengerUser user : this.requests) {
            response.writeInt(user.getUserId());
            response.writeString(user.getDetails().getName());
            response.writeString(user.getDetails().getFigure());
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.MessengerRequestsMessageComposer;
    }
}