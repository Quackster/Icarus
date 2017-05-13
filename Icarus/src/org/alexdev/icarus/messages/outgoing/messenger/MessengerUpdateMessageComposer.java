package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class MessengerUpdateMessageComposer extends OutgoingMessageComposer {

    private MessengerUser friend;
    private boolean forceOffline;

    public MessengerUpdateMessageComposer(MessengerUser friend, boolean forceOffline) {
        this.friend = friend;
        this.forceOffline = forceOffline;
    }

    @Override
    public void write() {
        
        response.init(Outgoing.FriendUpdateMessageComposer);
        response.writeInt(0);
        response.writeInt(1);
        response.writeInt(0);
        this.friend.serialiseFriend(response, this.forceOffline);
        response.writeBool(false);
        
    }

}
