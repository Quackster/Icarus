package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FriendsListMessageComposer extends MessageComposer {

    private List<MessengerUser> friends;

    public FriendsListMessageComposer(List<MessengerUser> friends) {
        this.friends = friends;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(1);
        response.writeInt(0);
        response.writeInt(this.friends.size());

        for (MessengerUser friend : this.friends) {
            friend.serialise(response, false);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.FriendsListMessageComposer;
    }
}