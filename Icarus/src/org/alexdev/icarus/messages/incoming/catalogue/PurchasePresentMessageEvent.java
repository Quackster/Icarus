package org.alexdev.icarus.messages.incoming.catalogue;

import java.util.List;

import org.alexdev.icarus.dao.mysql.InventoryDao;
import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.club.ClubManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.common.collect.Lists;

public class PurchasePresentMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

    	int pageId = request.readInt();
    	int itemId = request.readInt();
    	String presentData = request.readString();
    	String presentUser = request.readString();
    	String presentMessage = request.readString();
    	int spriteId = request.readInt();
    	int colour = request.readInt();
    	
    	
    }

}
