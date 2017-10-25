package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MessengerUpdateMessageComposer extends MessageComposer {

    private MessengerUser friend;
    private boolean forceOffline;

    public MessengerUpdateMessageComposer(MessengerUser friend, boolean forceOffline) {
        this.friend = friend;
        this.forceOffline = forceOffline;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FriendUpdateMessageComposer);
        response.writeInt(0);
        response.writeInt(1);
        response.writeInt(0);
        this.friend.serialise(response, this.forceOffline);
        response.writeBool(false);

    }

    @Override
    public short getHeader() {
        return Outgoing.FriendUpdateMessageComposer;
    }
}