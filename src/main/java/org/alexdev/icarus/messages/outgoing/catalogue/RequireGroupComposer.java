package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class RequireGroupComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.RequireGroupComposer); //TODO: Group search; [0][12]NewNavigator[0][6]Search[0][6]search[0][6]groups[0][0][0][0]
        this.response.writeInt(0);
        
        /*this.response.writeInt(1); // count
        this.response.writeInt(1337);
        this.response.writeString("Xd");
        this.response.writeString("s545505454454");
        this.response.writeString("");
        this.response.writeString("");
        this.response.writeBool(false);
        this.response.writeInt(1);//group.CreatorId);
        this.response.writeBool(false);//group.ForumEnabled);*/
    }

}
