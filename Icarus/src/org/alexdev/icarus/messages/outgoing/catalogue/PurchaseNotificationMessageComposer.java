package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class PurchaseNotificationMessageComposer implements OutgoingMessageComposer {
	
	private CatalogueItem item;
	private int amount;

	public PurchaseNotificationMessageComposer(CatalogueItem item, int amount) {
		this.item = item;
		this.amount = amount;
	}

	@Override
	public void write(AbstractResponse response) {
	
		response.init(Outgoing.PurchaseNotificationComposer);
		response.appendInt32(item.getData().getId());
		response.appendString(item.getData().getItemName());
		response.appendBoolean(false);
		response.appendInt32(item.getCostCredits());
		response.appendInt32(item.getCostPixels());
		response.appendInt32(0);
		response.appendBoolean(true);
		
		response.appendInt32(1); // amount
		
		response.appendString(item.getData().getType());
		response.appendInt32(item.getData().getSpriteId());
		response.appendString(item.getExtraData());
		response.appendInt32(amount);
		
		response.appendBoolean(item.isLimited());
		
		if (item.isLimited()) {
			response.appendInt32(item.getLimitedStack());
			response.appendInt32(item.getLimitedSells());
		}
		
		response.appendString(item.getSubscriptionStatus());
		response.appendInt32(1);
		
	}

}
