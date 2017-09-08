package org.alexdev.icarus.messages.outgoing.navigator;

import java.util.List;

import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class NavigatorMetaDataComposer extends MessageComposer {

    private List<NavigatorTab> tabs;

    public NavigatorMetaDataComposer(List<NavigatorTab> tabs) {
        this.tabs = tabs;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.NavigatorMetaDataComposer);
        this.response.writeInt(this.tabs.size());

        for (NavigatorTab tab : this.tabs) {
            this.response.writeString(tab.getTabName());
            this.response.writeInt(0);
        }
    }
}
