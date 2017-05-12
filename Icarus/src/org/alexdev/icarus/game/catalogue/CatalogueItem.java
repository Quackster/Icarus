package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.log.Log;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.furniture.FurnitureManager;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.netty.streams.NettyResponse;

import com.google.common.collect.Lists;

public class CatalogueItem {

    private int id;
    private int pageId;
    private String itemId;
    private String itemName;
    private int costCredits;
    private int costPixels;
    private int costOther;
    private int amount;
    private int subscriptionStatus;
    private int songId;
    private String extraData;
    private String badge;

    private int limitedTotal;
    private int limitedSells;

    private boolean hasOffer;
    private List<CatalogueBundledItem> items;

    public CatalogueItem(int id, int pageId, String itemIds, String catalogueName, int costCredits, int costPixels, int costOther, int amount, int subscriptionStatus, int songId, String extraData, String badage, int limitedStack, int limitedSells, boolean hasOffer) {
        this.id = id;
        this.pageId = pageId;
        this.itemId = itemIds;
        this.itemName = catalogueName;
        this.costCredits = costCredits;
        this.costPixels = costPixels;
        this.setCostOther(costOther);
        this.amount = amount;
        this.subscriptionStatus = subscriptionStatus;
        this.songId = songId;
        this.extraData = extraData;
        this.badge = badage;
        this.limitedTotal = limitedStack;
        this.limitedSells = limitedSells;
        this.hasOffer = hasOffer;
        this.items = Lists.newArrayList();

        // This has been taken from Comet, cus it's the best <3
        if (items.size() == 0) {
            if (!this.itemId.equals("-1")) {

                if (itemId.contains(",")) {
                    String[] split = itemId.replace("\n", "").split(",");

                    for (String str : split) {
                        if (!str.equals("")) {
                            String[] parts = str.split(":");
                            if (parts.length != 3) continue;

                            try {
                                final int aItemId = Integer.parseInt(parts[0]);
                                final int aAmount = Integer.parseInt(parts[1]);
                                final String aPresetData = parts[2];

                                this.items.add(new CatalogueBundledItem(this, aPresetData, aAmount, aItemId));
                            } catch (Exception ignored) {
                                Log.println("Invalid item data for catalog item: " + this.id);
                            }
                        }
                    }
                } else {
                    this.items.add(new CatalogueBundledItem(this, this.extraData, this.amount, Integer.valueOf(this.itemId)));
                }
            }
        }

        if (this.getItems().size() == 0) {
            return;
        }

        List<CatalogueBundledItem> itemsToRemove = new ArrayList<>();

        for (CatalogueBundledItem catalogBundledItem : this.items) {

            ItemDefinition itemDefinition = FurnitureManager.getFurnitureById(catalogBundledItem.getItemId());

            if (itemDefinition == null) {
                itemsToRemove.add(catalogBundledItem);
            }
        }

        for (CatalogueBundledItem itemToRemove : itemsToRemove) {
            this.items.remove(itemToRemove);
        }

        itemsToRemove.clear();
    }

    // All credits to Leon for this structure.
    public void serialise(Response response) {

        final ItemDefinition firstItem = this.itemId.equals("-1") ? null : FurnitureManager.getFurnitureById(this.getItems().get(0).getItemId());

        response.writeInt(this.getId());
        response.writeString(this.getCatalogueName());
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

        response.writeBool(firstItem != null && firstItem.allowGift());

        if (!this.hasBadge()) {
            response.writeInt(this.getItems().size());
        } else {
            response.writeInt(this.isBadgeOnly() ? 1 : this.getItems().size() + 1);
            response.writeString("b");
            response.writeString(this.getBadge());
        }

        if (!this.isBadgeOnly()) {
            for (CatalogueBundledItem bundledItem : this.getItems()) {
                ItemDefinition def = FurnitureManager.getFurnitureById(bundledItem.getItemId());

                response.writeString(def.getType());
                response.writeInt(def.getSpriteId());

                if (this.getCatalogueName().contains("wallpaper_single") || this.getCatalogueName().contains("floor_single") || this.getCatalogueName().contains("landscape_single")) {
                    response.writeString(this.getCatalogueName().split("_")[2]);
                } else {
                    response.writeString(bundledItem.getExtraData());
                }

                response.writeInt(bundledItem.getAmount());

                response.writeBool(this.getLimitedTotal() != 0);

                if (this.getLimitedTotal() > 0) {
                    response.writeInt(this.getLimitedTotal());
                    response.writeInt(this.getLimitedTotal() - this.getLimitedSells());
                }
            }
        }

        response.writeInt(this.subscriptionStatus);
        response.writeBool(!(this.getLimitedTotal() > 0) && this.allowOffer());
        response.writeBool(false);
        response.writeString("");

    }


    public int getId() {
        return id;
    }
    public int getPageId() {
        return pageId;
    }

    public String getItemId() {
        return itemId;
    }

    public List<CatalogueBundledItem> getItems() {
        return items;
    }

    public String getCatalogueName() {
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

    public int getSongId() {
        return songId;
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
        return !(this.badge.isEmpty());
    }

    public boolean isBadgeOnly() {
        return this.items.size() == 0 && this.hasBadge();
    }

    public boolean hasOffer() {
        return hasOffer;
    }

}
