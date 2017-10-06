package org.alexdev.icarus.game.item.interactions;

import org.alexdev.icarus.game.item.interactions.types.*;

public enum InteractionType {

    DEFAULT(new DefaultInteractor()),
    GATE(new GateInteractor()),
    BED(new BedInteractor()),
    VENDINGMACHINE(new VendingInteractor()),
    ONEWAYGATE(new OneWayGateInteractor()),
    DICE(new DiceInteractor()),
    TELEPORT(new TeleportInteractor()),
    MANNEQUIN(new MannequinInteractor()),
    DIMMER,
    BACKGROUND, 
    ROLLER,
    FLOOR,
    WALLPAPER,
    LANDSCAPE;

    private Interaction interaction;

    InteractionType() {
        this.interaction = null;
    }

    InteractionType(Interaction interaction) {
        this.interaction = interaction;
    }
    
    /**
     * Gets the handler.
     *
     * @return the handler
     */
    public Interaction getHandler() {
        return interaction;
    }
    
    /**
     * Gets the type by database name.
     *
     * @param databaseType the database type
     * @return the type
     */
    public static InteractionType getType(String databaseType) {
        
        
        for (InteractionType type : InteractionType.values()) {
            if (type.name().equals(databaseType.toUpperCase())) {
                return type;
            }
        }
        
        return InteractionType.DEFAULT;
    }
}
