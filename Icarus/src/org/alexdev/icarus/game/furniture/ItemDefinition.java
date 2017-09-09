package org.alexdev.icarus.game.furniture;

import org.alexdev.icarus.dao.mysql.item.InventoryDao;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
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
    private int spriteID;
    private boolean allowRecycle;
    private boolean allowTrade;
    private boolean allowMarketplaceSell;
    private boolean allowGift;
    private boolean allowInventoryStack;
    private InteractionType interactionType;
    private int interationModes;
    private String[] vendingIDs;
    private double[] variableHeight;

    public ItemDefinition(int id, String publicName, String itemName, String type, int width, int length, double stackHeight,
            boolean canStack, boolean canSit, boolean isWalkable, int spriteID, boolean allowRecycle,
            boolean allowTrade, boolean allowMarketplaceSell, boolean allowGift, boolean allowInventoryStack,
            InteractionType interactionType, int interationModes, String vendingIDs, String adjustableHeights) {

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
        this.spriteID = spriteID;
        this.allowRecycle = allowRecycle;
        this.allowTrade = allowTrade;
        this.allowMarketplaceSell = allowMarketplaceSell;
        this.allowGift = allowGift;
        this.allowInventoryStack = allowInventoryStack;
        this.interactionType = interactionType;
        this.interationModes = interationModes;
        this.vendingIDs = vendingIDs.isEmpty() ? new String[0] : vendingIDs.split(",");

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

    public void handleDefinitionPurchase(Player player, String extraData) {
        
        Item inventoryItem = InventoryDao.newItem(this.id, player.getDetails().getID(), extraData);

        if (inventoryItem.getDefinition().getInteractionType() == InteractionType.JUKEBOX) {
            inventoryItem.setExtraData("0");
        }

        if (inventoryItem.getDefinition().getInteractionType() == InteractionType.GATE) {
            inventoryItem.setExtraData("0");
        }

        if (inventoryItem.getDefinition().getInteractionType() == InteractionType.TELEPORT) {

            Item secondTeleporter = InventoryDao.newItem(this.id, player.getDetails().getID(), "0");

            inventoryItem.setExtraData(String.valueOf(secondTeleporter.getID()));
            secondTeleporter.setExtraData(String.valueOf(inventoryItem.getID()));

            player.getInventory().addItem(secondTeleporter);

            inventoryItem.save();
            secondTeleporter.save();

        }

        player.getInventory().addItem(inventoryItem);
    }
    
    public int getID() {
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

    public int getSpriteID() {
        return spriteID;
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

    public int getVendingID() {

        if (this.vendingIDs.length > 0) {
            int vendingID = Integer.parseInt(this.vendingIDs[Util.getRandom().nextInt(this.vendingIDs.length)].trim());
            return vendingID;
        }

        return -1;
    }

    public double[] getVariableHeight() {
        return variableHeight;
    }
}
