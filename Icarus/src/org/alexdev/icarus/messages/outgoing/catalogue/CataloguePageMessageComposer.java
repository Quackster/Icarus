package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class CataloguePageMessageComposer implements OutgoingMessageComposer {

    private CataloguePage page;
    private String type;

    public CataloguePageMessageComposer(CataloguePage page, String type) {
        this.page = page;
        this.type = type;
    }

    @Override
    public void write(AbstractResponse response) {

        response.init(Outgoing.CataloguePageMessageComposer);
        response.writeInt(this.page.getId());
        response.writeString(this.type);
        response.writeString(page.getLayout());
        
        response.writeInt(page.getImages().size());
        for (String image : page.getImages()) {
            response.writeString(image);
        }
        
       response.writeInt(page.getTexts().size());
        for (String text : page.getTexts()) {
            response.writeString(text);
        }

        response.writeInt(page.getItems().size()); 

        for (CatalogueItem item : page.getItems()) {
            item.serialise(response);
        }

        response.writeInt(0);
        response.writeBool(false);

    }

}
