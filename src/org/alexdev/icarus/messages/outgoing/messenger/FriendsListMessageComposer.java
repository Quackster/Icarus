package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class FriendsListMessageComposer extends MessageComposer {

    private List<MessengerUser> friends;

    public FriendsListMessageComposer(List<MessengerUser> friends) {
        this.friends = friends;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.InitMessengerMessageComposer);
        this.response.writeInt(1);
        this.response.writeInt(0);
        this.response.writeInt(this.friends.size());
        
        for (MessengerUser friend : this.friends) {
            friend.serialise(response, false);
        }
    }
}
