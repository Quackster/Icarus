package org.alexdev.icarus.game.furniture;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.util.Util;

public class ItemDefinition {

    private int id;
    private String publicName;
    private String itemName;
    private String type;
    private int width;
    private int length;
    private double height;
    //private boolean canStack;
    private boolean canSit;
    private boolean isWalkable;
    private int spriteId;
    private boolean allowRecycle;
    private boolean allowTrade;
    private boolean allowMarketplaceSell;
    private boolean allowGift;
    private boolean allowInventoryStack;
    private InteractionType interactionType;
    private int interationModes;
    private String[] vendingIds;
    private double[] variableHeight;

    public ItemDefinition(int id, String publicName, String itemName, String type, int width, int length, double stackHeight,
            boolean canStack, boolean canSit, boolean isWalkable, int spriteId, boolean allowRecycle,
            boolean allowTrade, boolean allowMarketplaceSell, boolean allowGift, boolean allowInventoryStack,
            InteractionType interactionType, int interationModes, String vendingIds, String adjustableHeights) {

        this.id = id;
        this.publicName = publicName;
        this.itemName = itemName;
        this.type = type;
        this.width = width;
        this.length = length;
        this.height = stackHeight;
        //this.canStack = canStack;
        this.canSit = canSit;
        this.isWalkable = isWalkable;
        this.spriteId = spriteId;
        this.allowRecycle = allowRecycle;
        this.allowTrade = allowTrade;
        this.allowMarketplaceSell = allowMarketplaceSell;
        this.allowGift = allowGift;
        this.allowInventoryStack = allowInventoryStack;
        this.interactionType = interactionType;
        this.interationModes = interationModes;
        this.vendingIds = vendingIds.isEmpty() ? new String[0] : vendingIds.split(",");

        if (adjustableHeights.length() > 0) {

            String[] parts = adjustableHeights.split(",");
            this.variableHeight = new double[parts.length];

            for (int i = 0; i < parts.length; i++) {
                this.variableHeight[i] = Double.parseDouble(parts[i]);
            }

        } else {
            this.variableHeight = new double[0];
        }
    }   

    public int getId() {
        return id;
    }

    public String getPublicName() {
        return publicName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public boolean allowStack() {

        if (this.canSit) {
            return false;
        }

        if (this.interactionType == InteractionType.GATE) {
            return false;
        }

        if (this.interactionType == InteractionType.TELEPORT) {
            return false;

        }
        if (this.interactionType == InteractionType.BED) {
            return false;

        }

        return true;
    }

    public boolean allowSit() {
        return canSit;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public int getSpriteId() {
        return spriteId;
    }

    public boolean allowRecycle() {
        return allowRecycle;
    }

    public boolean allowTrade() {
        return allowTrade;
    }

    public boolean allowMarketplaceSell() {
        return allowMarketplaceSell;
    }

    public boolean allowGift() {
        return allowGift;
    }

    public boolean allowInventoryStack() {
        return allowInventoryStack;
    }

    public boolean isRoller() {
        return interactionType == InteractionType.ROLLER;
    }

    public InteractionType getInteractionType() {
        return interactionType;
    }

    public int getInterationModes() {
        return interationModes;
    }

    public int getVendingId() {

        if (this.vendingIds.length > 0) {
            int vendingId = Integer.parseInt(this.vendingIds[Util.getRandom().nextInt(this.vendingIds.length)].trim());
            return vendingId;
        }

        return -1;
    }

    public double[] getVariableHeight() {
        return variableHeight;
    }
}
