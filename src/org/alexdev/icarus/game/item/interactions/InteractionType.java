package org.alexdev.icarus.game.item.interactions;

import org.alexdev.icarus.game.item.extradata.*;
import org.alexdev.icarus.game.item.extradata.types.*;
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
    DIMMER(null, new MoodlightDataReader()),
    BACKGROUND,
    ROLLER,
    FLOOR,
    WALLPAPER,
    LANDSCAPE,
    ROOMBG(null, new TonerDataReader());

    private Interaction interaction;
    private ExtraDataReader<?> extraData;

    InteractionType() {
        this.interaction = null;
    }

    InteractionType(Interaction interaction) {
        this.interaction = interaction;
    }
    
    InteractionType(Interaction interaction, ExtraDataReader<?> extraData) {
        this.interaction = interaction;
        this.extraData = extraData;
    }
    
    /**
     * Gets the handler.
     *
     * @return the handler
     */
    public Interaction getHandler() {
        return interaction;
    }
    
    public ExtraDataReader<?> getExtraDataReader() {
        return extraData;
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
