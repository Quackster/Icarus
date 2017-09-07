package org.alexdev.icarus.game.room;

import java.util.concurrent.atomic.AtomicInteger;

public class RoomPromotion {
    
    private String promotionName;
    private String promotionDescription;
    private AtomicInteger promotionMinutesLeft;
    private Room room;
    
    public RoomPromotion(Room room, String promotionName, String promotionDescri) {
        this.room = room;
        this.promotionName = promotionName;
        this.promotionDescription = promotionDescri;
        this.promotionMinutesLeft = new AtomicInteger(120);
    }
    
    public void performCycle() {
        
        if (this.room.getPromotion().getPromotionMinutesLeft().get() > 0) {
            this.room.getPromotion().getPromotionMinutesLeft().decrementAndGet();
        }
        
        // Time expired, too bad!
        if (!(this.room.getPromotion().getPromotionMinutesLeft().get() > 0)) {
            this.room.endPromotion();
        }
    }
    
    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionDescription() {
        return promotionDescription;
    }

    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    public AtomicInteger getPromotionMinutesLeft() {
        return promotionMinutesLeft;
    }

    public void setPromotionMinutesLeft(AtomicInteger promotionMinutesLeft) {
        this.promotionMinutesLeft = promotionMinutesLeft;
    }
}
