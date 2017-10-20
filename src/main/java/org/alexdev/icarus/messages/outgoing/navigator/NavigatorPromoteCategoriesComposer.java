package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class NavigatorPromoteCategoriesComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.NavigatorPromoteCategoriesComposer);
        response.writeInt(1);
        response.writeInt(1);
        response.writeString("Promoted Rooms");
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return Outgoing.NavigatorPromoteCategoriesComposer;
    }
}