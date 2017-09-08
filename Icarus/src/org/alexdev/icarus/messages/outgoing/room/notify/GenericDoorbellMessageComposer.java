package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class GenericDoorbellMessageComposer extends MessageComposer {

    private String username;
    private int notifyCode = -1;

    public GenericDoorbellMessageComposer(String username) {
        this.username = username;
    }

    public GenericDoorbellMessageComposer(int notifyCode) {
        this.notifyCode = notifyCode;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GenericDoorbellMessageComposer);

        if (this.username != null) {
            this.response.writeString(this.username); 
        } else {
            this.response.writeInt(this.notifyCode);
        }
    }
}
