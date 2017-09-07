package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.util.Util;

public class CatalogueTabMessageComposer extends MessageComposer {

    private String type;
    private List<CatalogueTab> parentTabs;

    public CatalogueTabMessageComposer(String type, List<CatalogueTab> parentTabs) {
        this.type = type;
        this.parentTabs = parentTabs;
    }

    @Override
    public void write() {
        response.init(Outgoing.CatalogueTabMessageComposer);
        response.writeBool(true);
        response.writeInt(0);
        response.writeInt(-1);
        response.writeString("root");
        response.writeString("");
        response.writeInt(0);
        response.writeInt(parentTabs.size());

        for (CatalogueTab parent_tab : parentTabs) {
            this.appendCatalogueIndexData(parent_tab, response);
            this.recursiveCatalogueIndex(parent_tab, response);
        }

        response.writeBool(false);
        response.writeString(type);
    }

    void appendCatalogueIndexData(CatalogueTab tab, Response response) {

        response.writeBool(tab.isEnabled());
        response.writeInt(tab.getIconImage());
        response.writeInt(tab.getId());
        response.writeString(tab.getLink().equals("undefined") ? Util.removeNonAlphaNumeric(tab.getCaption()).toLowerCase().replace(" ", "_") : tab.getLink());
        response.writeString(tab.getCaption());
        response.writeInt(0);
    }

    void recursiveCatalogueIndex(CatalogueTab tab, Response response)  {

        List<CatalogueTab> child_tabs = tab.getChildTabs();
        response.writeInt(child_tabs.size());

        for (CatalogueTab child_tab : child_tabs) {
            this.appendCatalogueIndexData(child_tab, response);
            this.recursiveCatalogueIndex(child_tab, response);
        }
    }
}
