package org.alexdev.icarus.game.item.interactions;

import org.alexdev.icarus.game.item.interactions.types.GuildFurniInteractor;
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
    DIMMER(new DimmerInteractor()),
    ROLLER(new RollerInteractor()),
    FLOOR(new FloorpaperInteractor()),
    WALLPAPER(new WallpaperInteractor()),
    LANDSCAPE(new LandscapeInteractor()),
    ROOMBG(new TonerInteractor()),
    BACKGROUND(new AdsBackgroundInteractor()),
    PHOTO(new PhotoInteractor()),
    GLD_GATE(new GuildFurniInteractor()),
    GLD_ITEM(new GuildFurniInteractor()),
    GLD_FORUM(new GuildFurniInteractor());

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
    public Interaction get() {
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
