package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.game.furniture.Furniture;
import org.alexdev.icarus.game.furniture.FurnitureManager;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class CatalogueItem {

	private int id;
	private int pageId;
	private int itemId;
	private String catalogueName;
	private int costCredits;
	private int costPixels;
	private int amount;
	private int subscriptionStatus;
	private int songId;
	private String extraData;
	private String badge;
	
	private int limitedStack;
	private int limitedSells;
	
	private boolean hasOffer;

	public void fill(int id, int pageId, int itemIds, String catalogueName, int costCredits, int costDuckets, 
						int amount, int subscriptionStatus, int songId, String extraData, String badage, int limitedStack, int limitedSells, 
						boolean hasOffer) {

		this.id = id;
		this.pageId = pageId;
		this.itemId = itemIds;
		this.catalogueName = catalogueName;
		this.costCredits = costCredits;
		this.costPixels = costDuckets;
		this.amount = amount;
		this.subscriptionStatus = subscriptionStatus;
		this.songId = songId;
		this.extraData = extraData;
		this.badge = badage;
		this.limitedStack = limitedStack;
		this.limitedSells = limitedSells;
		this.hasOffer = hasOffer;
	}

	
	public Furniture getData() {
		return FurnitureManager.getFurnitureById(this.itemId);
	}
	
	
	public void serialise(AbstractResponse response) {
		
		response.writeInt(this.id);
		response.writeString(this.catalogueName);
		response.writeBool(false);
		
        if (this.getCostPixels() == 0 && this.getCostCredits() == 0) {
            response.writeInt(this.costCredits);
            response.writeInt(0);
            
        } else  {
            response.writeInt(this.costCredits);
            response.writeInt(this.costPixels);
        }

        response.writeInt(0);///item.getQuestType());
        
        if (this.isLimited() || this.getData().getType().equals("r")) {
        	response.writeBool(false);
        } else {
        	response.writeBool(this.getData().allowGift());
        }
        
        response.writeInt(1); // is deal
        response.writeString(this.getData().getType());
        
        if (this.badge.length() > 0) {
        	
        	response.writeString(this.badge);
        	response.writeInt(this.subscriptionStatus);
        	response.writeInt(this.amount);
        } else {
        	
        	response.writeInt(this.getData().getSpriteId());
        	
        	if (this.catalogueName.contains("_single_")) {
            	response.writeString(this.catalogueName.split("_")[2]);
        	} else {
            	response.writeString(this.extraData);
        	}

        	response.writeInt(this.amount);
        	response.writeBool(this.isLimited()); 
        	
        	if (this.isLimited()) {
        		response.writeInt(this.limitedStack);
        		response.writeInt(this.limitedSells);
        	}
        	
        	response.writeInt(this.subscriptionStatus);
        	
        	if (this.isLimited()) {
        		response.writeBool(!this.isLimited() && this.hasOffer()); // && HaveOffer
        	} else {
        		response.writeBool(false);
        	}	
        }
	}
	
	
	public int getId() {
		return id;
	}
	public int getPageId() {
		return pageId;
	}
	
	public int getItemId() {
		return itemId;
	}
	public String getCatalogueName() {
		return catalogueName;
	}
	
	public int getCostCredits() {
		return costCredits;
	}
	public int getCostPixels() {
		return costPixels;
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
	
	public boolean isLimited() {
		return limitedStack > 0;
	}

	public int getLimitedStack() {
		return limitedStack;
	}

	public int getLimitedSells() {
		return limitedSells;
	}

	public boolean hasOffer() {
		return hasOffer;
	}

}
