package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class MessengerRequestsMessageComposer extends MessageComposer {

    private Player player;
    private List<MessengerUser> requests;

    public MessengerRequestsMessageComposer(Player player, List<MessengerUser> requests) {
        this.player = player;
        this.requests = requests;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.MessengerRequestsMessageComposer);
        this.response.writeInt(this.player.getDetails().getID());
        this.response.writeInt(this.requests.size()); 

        for (MessengerUser user : this.requests) {
            this.response.writeInt(user.getUserID());
            this.response.writeString(user.getDetails().getName());
            this.response.writeString(user.getDetails().getFigure());
        }
    }
}
