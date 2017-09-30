package org.alexdev.icarus.game.furniture;

import org.alexdev.icarus.dao.mysql.item.InventoryDao;
import org.alexdev.icarus.dao.mysql.item.TeleporterDao;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.inventory.InventoryNotification;
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
    private int spriteId;
    private boolean allowRecycle;
    private boolean allowTrade;
    private boolean allowMarketplaceSell;
    private boolean allowGift;
    private boolean allowInventoryStack;
    private InteractionType interactionType;
    private boolean requiresRights;
    private int interactionModes;
    private String[] vendingIds;
    private double[] variableHeight;

    public ItemDefinition(int id, String publicName, String itemName, String type, int width, int length, double stackHeight,
            boolean canStack, boolean canSit, boolean isWalkable, int spriteId, boolean allowRecycle,
            boolean allowTrade, boolean allowMarketplaceSell, boolean allowGift, boolean allowInventoryStack,
            InteractionType interactionType, boolean requiresRights, int interationModes, String vendingIds, String adjustableHeights) {

        this.id = id;
        this.publicName = publicName;
        this.itemName = itemName;
        this.type = type;
        this.width = width;
        this.length = length;
        this.height = stackHeight;
        this.canSit = canSit;
        this.isWalkable = isWalkable;
        this.spriteId = spriteId;
        this.allowRecycle = allowRecycle;
        this.allowTrade = allowTrade;
        this.allowMarketplaceSell = allowMarketplaceSell;
        this.allowGift = allowGift;
        this.allowInventoryStack = allowInventoryStack;
        this.interactionType = interactionType;
        this.setRequiresRights(requiresRights);
        this.interactionModes = interationModes;
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

    /**
     * Handle definition purchase.
     *
     * @param player the player
     * @param extraData the extra data
     */
    public void handleDefinitionPurchase(Player player, String extraData) {
        
        Item inventoryItem = InventoryDao.newItem(this.id, player.getEntityId(), extraData);

        if (inventoryItem.getDefinition().getInteractionType() == InteractionType.JUKEBOX) {
            inventoryItem.setExtraData("0");
        }

        if (inventoryItem.getDefinition().getInteractionType() == InteractionType.GATE) {
            inventoryItem.setExtraData("0");
        }
        
        if (inventoryItem.getDefinition().getInteractionType() == InteractionType.MANNEQUIN) {
            inventoryItem.setExtraData("m" + (char)5 + ".ch-210-1321.lg-285-92" + (char)5 + "Default Mannequin");
        }

        if (inventoryItem.getDefinition().getInteractionType() == InteractionType.TELEPORT) {

            Item secondTeleporter = InventoryDao.newItem(this.id, player.getEntityId(), "0");
            
            secondTeleporter.setTeleporterId(inventoryItem.getId());
            inventoryItem.setTeleporterId(secondTeleporter.getId());
            
            player.getInventory().addItem(secondTeleporter, InventoryNotification.ALERT);

            inventoryItem.save();
            secondTeleporter.save();

            TeleporterDao.savePair(inventoryItem.getId(), secondTeleporter.getId());
        }

        player.getInventory().addItem(inventoryItem, InventoryNotification.ALERT);
    }
    
    /**
     * Checks if is ads furni.
     *
     * @return true, if is ads furni
     */
    public boolean isAdsFurni() {
        return this.itemName.equals("ads_background");
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the public name.
     *
     * @return the public name
     */
    public String getPublicName() {
        return publicName;
    }

    /**
     * Gets the item name.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Allow stack.
     *
     * @return true, if successful
     */
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

    /**
     * Allow sit.
     *
     * @return true, if successful
     */
    public boolean allowSit() {
        return canSit;
    }
    
    /**
     * Allow sit or lay.
     *
     * @return true, if successful
     */
    public boolean allowSitOrLay() {
        return canSit || this.interactionType == InteractionType.BED;
    }

    /**
     * Checks if is walkable.
     *
     * @return true, if is walkable
     */
    public boolean isWalkable() {
        return isWalkable;
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
     * Allow recycle.
     *
     * @return true, if successful
     */
    public boolean allowRecycle() {
        return allowRecycle;
    }

    /**
     * Allow trade.
     *
     * @return true, if successful
     */
    public boolean allowTrade() {
        return allowTrade;
    }

    /**
     * Allow marketplace sell.
     *
     * @return true, if successful
     */
    public boolean allowMarketplaceSell() {
        return allowMarketplaceSell;
    }

    /**
     * Allow gift.
     *
     * @return true, if successful
     */
    public boolean allowGift() {
        return allowGift;
    }

    /**
     * Allow inventory stack.
     *
     * @return true, if successful
     */
    public boolean allowInventoryStack() {
        return allowInventoryStack;
    }

    /**
     * Checks if is roller.
     *
     * @return true, if is roller
     */
    public boolean isRoller() {
        return interactionType == InteractionType.ROLLER;
    }

    /**
     * Gets the interaction type.
     *
     * @return the interaction type
     */
    public InteractionType getInteractionType() {
        return interactionType;
    }

    public boolean requiresRights() {
        return requiresRights;
    }

    public void setRequiresRights(boolean requiresRights) {
        this.requiresRights = requiresRights;
    }

    /**
     * Gets the interaction modes.
     *
     * @return the interaction modes
     */
    public int getInteractionModes() {
        return interactionModes;
    }

    /**
     * Gets the vending id.
     *
     * @return the vending id
     */
    public int getVendingId() {

        if (this.vendingIds.length > 0) {
            int vendingId = Integer.parseInt(this.vendingIds[Util.getRandom().nextInt(this.vendingIds.length)].trim());
            return vendingId;
        }

        return -1;
    }

    /**
     * Gets the variable height.
     *
     * @return the variable height
     */
    public double[] getVariableHeight() {
        return variableHeight;
    }
}
