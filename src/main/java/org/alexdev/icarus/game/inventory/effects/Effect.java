package org.alexdev.icarus.game.inventory.effects;

import java.util.concurrent.atomic.AtomicInteger;

public class Effect {
    
    private int effectId;
    private boolean activated;

    private AtomicInteger timeLeft;
    private AtomicInteger quantity;
    
    public Effect(int effectId, boolean activated, int timeLeft, int quantity) {
        this.effectId = effectId;
        this.activated = activated;
        this.timeLeft = new AtomicInteger(timeLeft);
        this.quantity = new AtomicInteger(quantity);
    }

    /**
     * Gets the sprite id.
     *
     * @return the sprite id
     */
    public int getEffectId() {
        return effectId;
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
