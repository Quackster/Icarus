package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class RefreshFavouriteGroupComposer extends MessageComposer {

    private int userId;

    public RefreshFavouriteGroupComposer(int userId) {
        this.userId = userId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RefreshFavouriteGroupComposer);
        this.response.writeInt(this.userId);
    }
}
