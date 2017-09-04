package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class MessengerCategoriesMessageComposer extends MessageComposer {

    @Override
    public void write() {
        
        response.init(Outgoing.MessengerCategoriesMessageComposer);
        response.writeInt(300);//GameSettings.MAX_FRIENDS_DEFAULT); // get max friends
        response.writeInt(400);//GameSettings.MAX_FRIENDS_BASIC);
        response.writeInt(500);//GameSettings.MAX_FRIENDS_VIP);
        response.writeInt(0);
        
    }
}
