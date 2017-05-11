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
		response.writeInt(item.getData().getId());
		response.writeString(item.getData().getItemName());
		response.writeBool(false);
		response.writeInt(item.getCostCredits());
		response.writeInt(item.getCostPixels());
		response.writeInt(0);
		response.writeBool(true);
		
		response.writeInt(1); // amount
		
		response.writeString(item.getData().getType());
		response.writeInt(item.getData().getSpriteId());
		response.writeString(item.getExtraData());
		response.writeInt(amount);
		
		response.writeBool(item.isLimited());
		
		if (item.isLimited()) {
			response.writeInt(item.getLimitedStack());
			response.writeInt(item.getLimitedSells());
		}
		
		response.writeString(item.getSubscriptionStatus());
		response.writeInt(1);
		
	}

}
