package org.alexdev.icarus.messages.outgoing.user.club;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class ClubCenterMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.ClubCenterMessageComposer);
        response.writeInt(7 * 365); // streak duration in days
        response.writeString("01-01-2017"); // join date

        response.writeInt(1);
        response.writeInt(2);
        response.writeInt(3);
        response.writeInt(4);
        response.writeInt(5);

        response.writeInt(200); // coins spent
        response.writeInt(10); // % percentage of habbo creditz
        response.writeInt(100000); // payday countdown in minutes
    }

    @Override
    public short getHeader() {
        return Outgoing.ClubCenterMessageComposer;
    }
}