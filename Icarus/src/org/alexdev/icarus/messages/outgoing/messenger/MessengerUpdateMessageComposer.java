package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class MessengerUpdateMessageComposer extends MessageComposer {

    private MessengerUser friend;
    private boolean forceOffline;

    public MessengerUpdateMessageComposer(MessengerUser friend, boolean forceOffline) {
        this.friend = friend;
        this.forceOffline = forceOffline;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FriendUpdateMessageComposer);
        this.response.writeInt(0);
        this.response.writeInt(1);
        this.response.writeInt(0);
        this.friend.serialiseFriend(response, this.forceOffline);
        this.response.writeBool(false);
        
    }
}
