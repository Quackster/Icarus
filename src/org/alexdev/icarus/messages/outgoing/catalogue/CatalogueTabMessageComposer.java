package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
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
        this.response.init(Outgoing.CatalogueTabMessageComposer);
        this.response.writeBool(true);
        this.response.writeInt(0);
        this.response.writeInt(-1);
        this.response.writeString("root");
        this.response.writeString("");
        this.response.writeInt(0);
        this.response.writeInt(parentTabs.size());

        for (CatalogueTab parent_tab : parentTabs) {
            this.appendCatalogueIndexData(parent_tab, this.response);
            this.recursiveCatalogueIndex(parent_tab, this.response);
        }

        this.response.writeBool(false);
        this.response.writeString(type);
    }

    void appendCatalogueIndexData(CatalogueTab tab, Response response) {

        this.response.writeBool(tab.isEnabled());
        this.response.writeInt(tab.getIconImage());
        this.response.writeInt(tab.getId());
        this.response.writeString(tab.getLink().equals("undefined") ? Util.removeNonAlphaNumeric(tab.getCaption().toLowerCase()).replace(" ", "_") : tab.getLink());
        this.response.writeString(tab.getCaption());
        this.response.writeInt(0);
    }

    void recursiveCatalogueIndex(CatalogueTab tab, Response response)  {

        List<CatalogueTab> child_tabs = tab.getChildTabs();
        this.response.writeInt(child_tabs.size());

        for (CatalogueTab child_tab : child_tabs) {
            this.appendCatalogueIndexData(child_tab, this.response);
            this.recursiveCatalogueIndex(child_tab, this.response);
        }
    }
}
