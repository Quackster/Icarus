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
    
    /**
     * Will decrease the room promotion timer by 1 down every minute.
     * If the timer has expired, then the promotion has ended and will be removed.
     */
    public void performCycle() {
        if (this.room.getPromotion().getPromotionMinutesLeft().get() > 0) {
            this.room.getPromotion().getPromotionMinutesLeft().decrementAndGet();
        }
        
        // Time expired, too bad!
        if (!(this.room.getPromotion().getPromotionMinutesLeft().get() > 0)) {
            this.room.endPromotion();
        }
    }
    
    /**
     * Gets the promotion name.
     *
     * @return the promotion name
     */
    public String getPromotionName() {
        return promotionName;
    }

    /**
     * Sets the promotion name.
     *
     * @param promotionName the new promotion name
     */
    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    /**
     * Gets the promotion description.
     *
     * @return the promotion description
     */
    public String getPromotionDescription() {
        return promotionDescription;
    }

    /**
     * Sets the promotion description.
     *
     * @param promotionDescription the new promotion description
     */
    public void setPromotionDescription(String promotionDescription) {
        this.promotionDescription = promotionDescription;
    }

    /**
     * Gets the promotion minutes left.
     *
     * @return the promotion minutes left
     */
    public AtomicInteger getPromotionMinutesLeft() {
        return promotionMinutesLeft;
    }
}
