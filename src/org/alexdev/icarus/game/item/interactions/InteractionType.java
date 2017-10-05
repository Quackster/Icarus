package org.alexdev.icarus.game.item.interactions;

import org.alexdev.icarus.game.item.interactions.types.AdjustableHeightInteractor;
import org.alexdev.icarus.game.item.interactions.types.BedInteractor;
import org.alexdev.icarus.game.item.interactions.types.DefaultInteractor;
import org.alexdev.icarus.game.item.interactions.types.DiceInteractor;
import org.alexdev.icarus.game.item.interactions.types.DimmerInteractor;
import org.alexdev.icarus.game.item.interactions.types.GateInteractor;
import org.alexdev.icarus.game.item.interactions.types.MannequinInteractor;
import org.alexdev.icarus.game.item.interactions.types.OneWayGateInteractor;
import org.alexdev.icarus.game.item.interactions.types.TeleportInteractor;
import org.alexdev.icarus.game.item.interactions.types.VendingInteractor;

public enum InteractionType {

    DEFAULT(new DefaultInteractor()),
    GATE(new GateInteractor()),
    ROOMEFFECT,
    DIMMER(new DimmerInteractor()),
    BED(new BedInteractor()),
    VENDINGMACHINE(new VendingInteractor()),
    ADJUSTABLEHEIGHT(new AdjustableHeightInteractor()),
    ONEWAYGATE(new OneWayGateInteractor()),
    DICE(new DiceInteractor()),
    TELEPORT(new TeleportInteractor()),
    MANNEQUIN(new MannequinInteractor()),
    BACKGROUND(null), 
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
    
    public Interaction getHandler() {
        return interaction;
    }
    
    public static InteractionType getType(String databaseType) {
        try {
            return InteractionType.valueOf(databaseType.toUpperCase().replace("_", ""));
        } catch (Exception e) {
            return InteractionType.DEFAULT;
        }
    }
}
