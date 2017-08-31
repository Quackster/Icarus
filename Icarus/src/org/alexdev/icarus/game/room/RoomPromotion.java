package org.alexdev.icarus.game.room;

public class RoomPromotion {
	
	private String promotionName;
	private String promotionDescription;
	
	public RoomPromotion(String promotionName, String promotionDescri) {
		this.promotionName = promotionName;
		this.promotionDescription = promotionDescri;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public String getPromotionDescription() {
		return promotionDescription;
	}

	public int getMinutesLeft() {
		return 10;
	}
	
	
}
