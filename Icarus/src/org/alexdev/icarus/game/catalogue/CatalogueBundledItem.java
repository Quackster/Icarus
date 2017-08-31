package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.game.furniture.ItemManager;

public class CatalogueBundledItem {

    private String extraData;
    private int amount;
    private int itemId;
    private CatalogueItem catalogueItem;
    private ItemDefinition overrideDefinition;
    
    public CatalogueBundledItem(CatalogueItem catalogueItem, String extraData, int amount, int itemId) {
        this.catalogueItem = catalogueItem;
        this.extraData = extraData;
        this.amount = amount;
        this.itemId = itemId;
    }

    public CatalogueItem getCatalogueItem() {
        return catalogueItem;
    }

    public String getExtraData() {
        return extraData;
    }

    public int getAmount() {
        return amount;
    }

    public ItemDefinition getItemDefinition() {
    	
    	if (this.overrideDefinition != null) {
    		return this.overrideDefinition;
    	}
    	
        return ItemManager.getFurnitureById(this.itemId);
    }

	public void createBadgeDefinition() {
		this.overrideDefinition = new ItemDefinition(this.catalogueItem.getCatalogueName(), "b");
	}


}
