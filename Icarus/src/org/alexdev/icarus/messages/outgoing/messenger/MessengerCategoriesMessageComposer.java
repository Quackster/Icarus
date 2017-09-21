package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class MessengerCategoriesMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.MessengerCategoriesMessageComposer);
        this.response.writeInt(300);//GameSettings.MAX_FRIENDS_DEFAULT); // get max friends
        this.response.writeInt(400);//GameSettings.MAX_FRIENDS_BASIC);
        this.response.writeInt(500);//GameSettings.MAX_FRIENDS_VIP);
        this.response.writeInt(0);
        
    }
}
