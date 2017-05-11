package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.CataloguePageMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CataloguePageMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {
		
		int pageId = request.readInt();
		
		CataloguePage page = CatalogueManager.getPage(pageId);
		
		if (page == null) {
			return;
		}
		
		player.send(new CataloguePageMessageComposer(page, "NORMAL"));
	}

}
