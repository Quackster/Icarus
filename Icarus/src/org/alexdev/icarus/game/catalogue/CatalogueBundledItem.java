package org.alexdev.icarus.game.catalogue;

public class CatalogueBundledItem {

    private String extraData;
    private int amount;
    private int itemId;
    private CatalogueItem catalogueItem;
    
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

    public int getItemId() {
        return itemId;
    }

}
