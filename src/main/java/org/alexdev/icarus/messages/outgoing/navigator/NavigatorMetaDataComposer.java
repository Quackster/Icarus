package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class NavigatorMetaDataComposer extends MessageComposer {

    private List<NavigatorTab> tabs;

    public NavigatorMetaDataComposer(List<NavigatorTab> tabs) {
        this.tabs = tabs;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.NavigatorMetaDataComposer);
        response.writeInt(this.tabs.size());

        for (NavigatorTab tab : this.tabs) {
            response.writeString(tab.getTabName());
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.NavigatorMetaDataComposer;
    }
}