package org.alexdev.icarus.game.player.club;

import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;

public class ClubManager {

	public static void handlePurchase(Player player, CatalogueBundledItem bundleItem) {
		
		int daysPurchased = 30;
		String catalogueName = bundleItem.getCatalogueItem().getCatalogueName();
		
		if (catalogueName.equals("DEAL_HC_2")) {
			daysPurchased = daysPurchased * 3;
		}
		
		if (catalogueName.equals("DEAL_HC_3")) {
			daysPurchased = daysPurchased * 6;
		}
		
		player.send(new PurchaseNotificationMessageComposer(bundleItem));
		
		Log.println("Days of Habbo Club purchased: " + daysPurchased);
	}

}
