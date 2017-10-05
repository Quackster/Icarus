package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.messages.Response;

public class CatalogueItem {

    private int id;
    private int pageId;
    private Integer itemId;
    private String itemName;
    private int costCredits;
    private int costPixels;
    private int costOther;
    private int amount;
    private int subscriptionStatus;
    private String extraData;
    private String badge;

    private int limitedTotal;
    private int limitedSells;

    private boolean hasOffer;

    public CatalogueItem(int id, int pageId, String itemIds, String catalogueName, int costCredits, int costPixels, int costOther, int amount, String extraData, String badage, int limitedStack, int limitedSells, boolean hasOffer) {
        try {
            this.id = id;
            this.pageId = pageId;
            this.itemId = Integer.valueOf(itemIds);
            this.itemName = catalogueName;
            this.costCredits = costCredits;
            this.costPixels = costPixels;
            this.setCostOther(costOther);
            this.amount = amount;
            this.extraData = extraData;
            this.badge = badage;
            this.limitedTotal = limitedStack;
            this.limitedSells = limitedSells;
            this.hasOffer = hasOffer;
            this.subscriptionStatus = 0;
        } catch (NumberFormatException e) {
            Log.info("Error loading furniture definition: " + this.id);
            e.printStackTrace();
        }
    }

    // All credits to Leon for this structure.
    public void serialise(Response response, boolean debugFurniture) {

        /*
         * 
         * Incoming[1574, _-6Vt, CatalogPageMessageParser] <- 
         * 
         * [0][0][0]y[6]&[0][11]J>
         * [0][6]NORMAL // type
         * [0][7]roomads // layout
         * [0][0][0][2] // 2 headers, 1 blank
         * [0][13]events_header
         * [0][0]
         * 
         * [0][0][0][2] // 2 blank images
         * [0][0]
         * [0][0]
         * 
         * [0][0][0][1] // 1 item
         * 
         * [0][0]-Û // item id
         * [0]room_ad_plus_badge // catalogue name
         * [0]
         * [0][0][0][0] // cost credits
         * [0][0][0] // cost pixels
         * 
         * [0][0][0][0] // other shit??
         * [0][0][0][0] // other shit??
         * 
         * [0] // first item doesn't equal number and allow gift? = false
         * 
         * [1] // size?
         * 
         * [1]b // badge
         * [0][5]RADZZ // badge type
         * 
         * [0][0][0][0] // sub status
         * 
         * [0] // can bundle deal??
         * [0] // false??
         * [0][0] // empty string????
         * 
         * -- not item related beyon this point of the packet
         * ÿÿÿÿ[0]            
         * 
         */

        final ItemDefinition def = this.getItemDefinition();

        response.writeInt(this.getId());
        response.writeString(this.getDisplayName() + (debugFurniture ? " (Definition Id " + def.getId() + ")" : "") +  (debugFurniture ? " (" + this.pageId + ")" : ""));
        response.writeBool(false);

        response.writeInt(this.getCostCredits());

        if (this.getCostOther() > 0) {
            response.writeInt(this.getCostOther());
            response.writeInt(105);
        } else if (this.getCostPixels() > 0) {
            response.writeInt(this.getCostPixels());
            response.writeInt(0);
        } else {
            response.writeInt(0);
            response.writeInt(0);
        }

        response.writeBool(def.allowGift());
        response.writeInt(this.hasBadge() ? 2 :1);

        if (this.hasBadge()) {
            response.writeString(ItemType.BADGE.toString());
            response.writeString(this.badge);
        }
        
        response.writeString(def.getType().toString());
        
        if (def.getType() == ItemType.BADGE) {
            response.writeString(def.getItemName());
        } else {
            response.writeInt(def.getSpriteId());

            if (this.getDisplayName().contains("wallpaper_single") || this.getDisplayName().contains("floor_single") || this.getDisplayName().contains("landscape_single")) {
                response.writeString(this.getDisplayName().split("_")[2]);
            } else {
                response.writeString(this.extraData);
            }

            response.writeInt(this.amount);
            response.writeBool(this.getLimitedTotal() != 0);

            if (this.getLimitedTotal() > 0) {
                response.writeInt(this.getLimitedTotal());
                response.writeInt(this.getLimitedTotal() - this.getLimitedSells());
            }
        }

        response.writeInt(this.subscriptionStatus);
        response.writeBool(!(this.getLimitedTotal() > 0) && this.allowOffer());
        response.writeBool(false);
        response.writeString("test.png");
    }

    public ItemDefinition getItemDefinition() {
        ItemDefinition def = ItemManager.getFurnitureById(this.itemId);
        if (def == null) {
            def = ItemManager.getFurnitureById(1);
        }
        return def;
    }

    public int getId() {
        return id;
    }
    public int getPageId() {
        return pageId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public String getDisplayName() {
        return itemName;
    }

    public int getCostCredits() {
        return costCredits;
    }
    public int getCostPixels() {
        return costPixels;
    }

    public int getCostOther() {
        return costOther;
    }

    public void setCostOther(int costOther) {
        this.costOther = costOther;
    }

    public int getAmount() {
        return amount;
    }

    public int getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public String getExtraData() {
        return extraData;
    }

    public String getBadge() {
        return badge;
    }

    public int getLimitedTotal() {
        return this.limitedTotal;
    }

    public int getLimitedSells() {
        return this.limitedSells;
    }

    public boolean allowOffer() {
        return this.hasOffer;
    }

    public void increaseLimitedSells(int amount) {
        this.limitedSells += amount;
    }

    public boolean hasBadge() {
        return this.badge.length() > 0;
    }
}
