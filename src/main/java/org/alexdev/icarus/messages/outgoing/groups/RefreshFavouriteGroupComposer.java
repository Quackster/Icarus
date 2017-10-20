package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class RefreshFavouriteGroupComposer extends MessageComposer {

    private int userId;

    public RefreshFavouriteGroupComposer(int userId) {
        this.userId = userId;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.userId);
    }

    @Override
    public short getHeader() {
        return Outgoing.RefreshFavouriteGroupComposer;
    }
}
