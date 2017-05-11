package org.alexdev.icarus.messages.incoming.catalogue;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CatalogueTab;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.CatalogueTabMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CatalogueMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {
	
		String type = request.readString();
		
		if (type == null) {
			return;
		}
		
		List<CatalogueTab> parentTabs = CatalogueManager.getParentTabs(player.getDetails().getId());
		
		if (parentTabs == null) {
			return;
		}
		
		player.send(new CatalogueTabMessageComposer(type, parentTabs));
	}

}
