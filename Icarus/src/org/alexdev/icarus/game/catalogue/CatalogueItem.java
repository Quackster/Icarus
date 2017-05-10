package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.furniture.Furniture;
import org.alexdev.icarus.game.furniture.FurnitureManager;
import org.alexdev.icarus.server.messages.AbstractResponse;

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

	/*				item.fill(row.getInt("id"), row.getInt("page_id"), row.getInt("item_ids"), row.getString("catalog_name"), 
							row.getInt("cost_credits"), row.getInt("cost_pixels"), row.getInt("cost_belcredits"), row.getInt("amount"), 
							row.getInt("item_subscription_status"), row.getInt("quest_type"), row.getInt("song_id"), row.getString("extradata"),
							row.getString("badge"), row.getInt("limited_stack"), row.getInt("limited_sells"), row.getInt("offer_active") == 1);*/
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
		
		response.appendInt32(this.id);
		response.appendString(this.catalogueName);
		response.appendBoolean(false);
		
        if (this.getCostPixels() == 0 && this.getCostCredits() == 0) {
            response.appendInt32(this.costCredits);
            response.appendInt32(0);
            
        } else  {
            response.appendInt32(this.costCredits);
            response.appendInt32(this.costPixels);
        }

        response.appendInt32(0);///item.getQuestType());
        
        if (this.isLimited() || this.getData().getType().equals("r")) {
        	response.appendBoolean(false);
        } else {
        	response.appendBoolean(this.getData().allowGift());
        }
        
        response.appendInt32(1); // is deal
        response.appendString(this.getData().getType());
        
        if (this.badge.length() > 0) {
        	
        	response.appendString(this.badge);
        	response.appendInt32(this.subscriptionStatus);
        	response.appendInt32(this.amount);
        } else {
        	
        	response.appendInt32(this.getData().getSpriteId());
        	
        	if (this.catalogueName.contains("_single_")) {
            	response.appendString(this.catalogueName.split("_")[2]);
        	} else {
            	response.appendString(this.extraData);
        	}

        	response.appendInt32(this.amount);
        	response.appendBoolean(this.isLimited()); 
        	
        	if (this.isLimited()) {
        		response.appendInt32(this.limitedStack);
        		response.appendInt32(this.limitedSells);
        	}
        	
        	response.appendInt32(this.subscriptionStatus);
        	
        	if (this.isLimited()) {
        		response.appendBoolean(!this.isLimited() && this.hasOffer()); // && HaveOffer
        	} else {
        		response.appendBoolean(false);
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
