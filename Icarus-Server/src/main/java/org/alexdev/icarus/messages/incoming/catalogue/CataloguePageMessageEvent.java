package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.catalogue.CatalogueDiscountComposer;
import org.alexdev.icarus.messages.outgoing.catalogue.CataloguePageMessageComposer;
import org.alexdev.icarus.messages.outgoing.catalogue.GroupFurniConfigMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CataloguePageMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        int pageId = request.readInt();

        CataloguePage page = CatalogueManager.getInstance().getPage(pageId);

        if (page == null) {
            return;
        }

        request.readInt();

        player.send(new CataloguePageMessageComposer(page, request.readString(), player.getMetadata().getBoolean("debugfurniture")));
        player.send(new CatalogueDiscountComposer());
    }
}
