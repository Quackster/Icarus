package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MessengerCategoriesMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.MessengerCategoriesMessageComposer);
        response.writeInt(300);//GameSettings.MAX_FRIENDS_DEFAULT); // getInteractor max friends
        response.writeInt(400);//GameSettings.MAX_FRIENDS_BASIC);
        response.writeInt(500);//GameSettings.MAX_FRIENDS_VIP);
        response.writeInt(0);
    }

    @Override
    public short getHeader() {
        return Outgoing.MessengerCategoriesMessageComposer;
    }
}
