package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

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
