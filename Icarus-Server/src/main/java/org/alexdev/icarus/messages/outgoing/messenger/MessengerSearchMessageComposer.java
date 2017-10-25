package org.alexdev.icarus.messages.outgoing.messenger;

import java.util.List;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class MessengerSearchMessageComposer extends MessageComposer {

    private List<MessengerUser> friends;
    private List<MessengerUser> strangers;

    public MessengerSearchMessageComposer(List<MessengerUser> friends, List<MessengerUser> strangers) {
        this.friends = friends;
        this.strangers = strangers;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.MessengerSearchMessageComposer);
        response.writeInt(this.friends.size());

        for (MessengerUser friend : this.friends) {
            this.serialise(response, friend);
        }

        response.writeInt(this.strangers.size());

        for (MessengerUser stranger : this.strangers) {
            this.serialise(response, stranger);
            stranger.dispose();
        }
    }

    private void serialise(Response response, MessengerUser user) {
        response.writeInt(user.getDetails().getId());
        response.writeString(user.getDetails().getName());
        response.writeString(user.getDetails().getMission());
        response.writeBool(user.isUserOnline());
        response.writeBool(user.inRoom());
        response.writeString("");
        response.writeInt(0);

        if (user.isUserOnline()) {
            response.writeString(user.getDetails().getFigure());
        } else {
            response.writeString("");
        }

        response.writeInt(0);
        response.writeInt(0);
    }

    @Override
    public short getHeader() {
        return Outgoing.MessengerSearchMessageComposer;
    }
}