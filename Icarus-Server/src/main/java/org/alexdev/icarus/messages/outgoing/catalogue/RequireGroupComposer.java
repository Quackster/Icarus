package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RequireGroupComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RequireGroupComposer); //TODO: Group search; [0][12]NewNavigator[0][6]Search[0][6]search[0][6]groups[0][0][0][0]
        response.writeInt(0);
        
        /*response.writeInt(1); // count
        response.writeInt(1337);
        response.writeString("Xd");
        response.writeString("s545505454454");
        response.writeString("");
        response.writeString("");
        response.writeBool(false);
        response.writeInt(1);//group.CreatorId);
        response.writeBool(false);//group.ForumEnabled);*/
    }

    @Override
    public short getHeader() {
        return Outgoing.RequireGroupComposer;
    }
}