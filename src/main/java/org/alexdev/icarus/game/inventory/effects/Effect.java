package org.alexdev.icarus.game.inventory.effects;

import java.util.concurrent.atomic.AtomicInteger;

public class Effect {
    
    private int spriteId;
    private boolean activated;
    
    private AtomicInteger duration;
    private AtomicInteger timeLeft;
    private AtomicInteger quantity;
    
    public Effect(int spriteId, int duration, boolean activated, int timeLeft, int quantity) {
        this.spriteId = spriteId;
        this.activated = activated;
        this.duration = new AtomicInteger(duration);
        this.timeLeft = new AtomicInteger(timeLeft);
        this.quantity = new AtomicInteger(quantity);
    }

    /**
     * Gets the sprite id.
     *
     * @return the sprite id
     */
    public int getSpriteId() {
        return spriteId;
    }
    
    /**
     * Sets the sprite id.
     *
     * @param spriteId the new sprite id
     */
    public void setSpriteId(int spriteId) {
        this.spriteId = spriteId;
    }
    
    /**
     * Gets the duration.
     *
     * @return the duration
     */
    public AtomicInteger getDuration() {
        return duration;
    }
    
    /**
     * Checks if is activated.
     *
     * @return true, if is activated
     */
    public boolean isActivated() {
        return activated;
    }
    
    /**
     * Sets the activated.
     *
     * @param activated the new activated
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }
    
    /**
     * Gets the time left.
     *
     * @return the time left
     */
    public AtomicInteger getTimeLeft() {
        return timeLeft;
    }
    
    /**
     * Gets the quantity.
     *
     * @return the quantity
     */
    public AtomicInteger getQuantity() {
        return quantity;
    }
}
