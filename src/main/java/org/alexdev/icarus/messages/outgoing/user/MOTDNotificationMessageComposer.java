package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class MOTDNotificationMessageComposer extends MessageComposer {

    private String message;

    public MOTDNotificationMessageComposer(String message) {
        this.message = message;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.MOTDNotificationMessageComposer);
        this.response.writeInt(1);
        this.response.writeString(this.message);
    }

}
