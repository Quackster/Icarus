package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.messages.Response;

public class CatalogueItem {

    private int id;
    private int pageId;
    private int itemId;
    private String itemName;
    
    private int costCredits;
    private int costOtherType;
    private int costOther;
    
    private int amount;
    private boolean buyMultiple;
    private int subscriptionStatus;
    
    private String extraData;
    private String badge;
    private int limitedStack;
    private int limitedSells;
    private boolean hasOffer;

    public CatalogueItem(int id, int pageId, String itemIds, String catalogueName, int costCredits, int costOtherType, int costOther, int amount, boolean buyMultiple, int subscriptionStatus, String extraData, String badage, int limitedStack, int limitedSells, boolean hasOffer) {
        try {
            this.id = id;
            this.pageId = pageId;
            this.itemId = Integer.valueOf(itemIds);
            this.itemName = catalogueName;
            this.costCredits = costCredits;
            this.costOtherType = costOtherType;
            this.costOther = costOther;
            this.amount = amount;
            this.buyMultiple = buyMultiple;
            this.subscriptionStatus = subscriptionStatus;
            this.extraData = extraData;
            this.badge = badage;
            this.limitedStack = limitedStack;
            this.limitedSells = limitedSells;
            this.hasOffer = hasOffer;
        } catch (NumberFormatException e) {
            Log.info("Error loading furniture definition: " + this.id);
            e.printStackTrace();
        }
    }

    /**
     * Serialise.
     *
     * @param response the response
     * @param debugFurniture the debug furniture
     */
    public void serialise(Response response, boolean debugFurniture) {

        final ItemDefinition def = this.getItemDefinition();

        response.writeInt(this.getId());
        response.writeString(this.getDisplayName() + (debugFurniture ? " (Definition Id " + def.getId() + ")" : "") +  (debugFurniture ? " (" + this.pageId + ")" : ""));
        response.writeBool(false);

        response.writeInt(this.getCostCredits());

        if (this.getCostOther() > 0) {
            response.writeInt(this.getCostOther());
            response.writeInt(this.getCostOtherType());
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
            response.writeBool(this.getLimitedStack() != 0);

            if (this.getLimitedStack() > 0) {
                response.writeInt(this.getLimitedStack());
                response.writeInt(this.getLimitedStack() - this.getLimitedSells());
            }
        }

        response.writeInt(this.subscriptionStatus);
        response.writeBool(this.buyMultiple); // can we buy more than 1?
        response.writeBool(false);
        response.writeString("test.png");
    }

    /**
     * Gets the item definition.
     *
     * @return the item definition
     */
    public ItemDefinition getItemDefinition() {
        ItemDefinition def = ItemManager.getFurnitureById(this.itemId);
        if (def == null) {
            def = ItemManager.getFurnitureById(1);
        }
        return def;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
    
    /**
     * Gets the page id.
     *
     * @return the page id
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * Gets the item id.
     *
     * @return the item id
     */
    public Integer getItemId() {
        return itemId;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return itemName;
    }

    /**
     * Gets the cost credits.
     *
     * @return the cost credits
     */
    public int getCostCredits() {
        return costCredits;
    }
    
    /**
     * Gets the other cost type.
     *
     * @return the cost type
     */
    public int getCostOtherType() {
        return costOtherType;
    }
    
    /**
     * Gets the other cost type.
     *
     * @return the cost type
     */
    public int getOtherCost() {
        return costOther;
    }

    /**
     * Gets the cost other.
     *
     * @return the cost other
     */
    public int getCostOther() {
        return costOther;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the subscription status.
     *
     * @return the subscription status
     */
    public int getSubscriptionStatus() {
        return subscriptionStatus;
    }

    /**
     * Gets the extra data.
     *
     * @return the extra data
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Gets the badge.
     *
     * @return the badge
     */
    public String getBadge() {
        return badge;
    }

    /**
     * Gets the limited total.
     *
     * @return the limited total
     */
    public int getLimitedStack() {
        return this.limitedStack;
    }

    /**
     * Gets the limited sells.
     *
     * @return the limited sells
     */
    public int getLimitedSells() {
        return this.limitedSells;
    }

    /**
     * Allow offer.
     *
     * @return true, if successful
     */
    public boolean allowOffer() {
        return this.hasOffer;
    }

    /**
     * Increase limited sells.
     *
     * @param amount the amount
     */
    public void increaseLimitedSells(int amount) {
        this.limitedSells += amount;
    }

    /**
     * Checks for badge.
     *
     * @return true, if successful
     */
    public boolean hasBadge() {
        return this.badge.length() > 0;
    }
}
