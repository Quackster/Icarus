package org.alexdev.icarus.factories;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CatalogueTab;

public class CatalogueFactory {

	public static CatalogueTab getTab(int id, int parentId, String caption, int iconColour, int iconImage, boolean enabled, int minRank)  {
		CatalogueTab tab = new CatalogueTab();
		tab.fill(id, parentId, caption, iconColour, iconImage, enabled, minRank);
		return tab;
	}
	
	public static CatalogueItem getItem(int id, int pageId, int itemIds, String catalogueName, int costCredits, int costDuckets,int amount, int subscriptionStatus, int songId, String extraData, String badage, int limitedStack, int limitedSells,  boolean hasOffer) {
		CatalogueItem item = new CatalogueItem();
		item.fill(id, pageId, itemIds, catalogueName, costCredits, costDuckets, amount, subscriptionStatus, songId, extraData, badage, limitedStack, limitedSells, hasOffer);
		return item;
	}
	
}
