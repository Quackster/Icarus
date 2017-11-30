package org.alexdev.icarus.game.item;

import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.items.MoveItemMessageComposer;
import org.alexdev.icarus.util.Util;
import org.alexdev.icarus.util.metadata.Metadata;

import java.util.ArrayList;
import java.util.List;

public class Item extends Metadata {

    private int id;
    private int ownerId;
    private int userId;
    private String ownerName;
    private int itemId;
    private int roomId;
    private Position position;
    private String extraData;
    private Item itemUnderneath;
    private int lengthX = 0;
    private int lengthY = 0;
    private char side = 0;
    private int widthX = 0;
    private int widthY = 0;

    public Item(int id, int ownerId, int userId, int itemId, int roomId, String x, String y, double z, int rotation, String extraData) {

        this.id = id;
        this.ownerId = ownerId;
        this.userId = userId;
        this.ownerName = PlayerDao.getName(this.ownerId);
        this.itemId = itemId;
        this.roomId = roomId;
        this.extraData = extraData;
        this.itemUnderneath = null;
        this.position = new Position();

        if (this.getDefinition().getType() == ItemType.FLOOR) {
            this.position.setX(Integer.parseInt(x));
            this.position.setY(Integer.parseInt(y));
            this.position.setZ(z);
            this.position.setRotation(rotation);
        }

        if (this.getDefinition().getType() == ItemType.WALL) {
            if (this.roomId > 0) {
                this.parseWallPosition(x + " " + y);
            }
        }
    }

    /**
     * Updates entities who are or were sitting/laying/standing on this furniture. It can take a
     * "previous" position instance and check that before processing the item's current coordinates.
     *
     * By default it will only check item's current position if a null parameter is passed.
     *
     * @param previousPosition the previous position (for example, when moving or rotating furniture).
     */
    public void updateEntities(Position previousPosition) {

        if (this.getDefinition().getType() != ItemType.FLOOR) {
            return;
        }

        List<Entity> entitiesToUpdate = new ArrayList<>();

        if (previousPosition != null) {
            List<Position> previousTiles = AffectedTile.getAffectedTiles(previousPosition, this.getDefinition());
            previousTiles.add(previousPosition);

            for (Position position :  previousTiles) {
                entitiesToUpdate.addAll(this.getRoom().getMapping().getTile(position.getX(), position.getY()).getEntities());
            }
        }

        if (this.position != null) {
            List<Position> newTiles = AffectedTile.getAffectedTiles(this.position, this.getDefinition());
            newTiles.add(this.position);

            for (Position position :  newTiles) {
                entitiesToUpdate.addAll(this.getRoom().getMapping().getTile(position.getX(), position.getY()).getEntities());
            }
        }

        for (Entity entity : entitiesToUpdate) {
            entity.getRoomUser().refreshItemStatus();
        }
    }

    /**
     * Checks if is walkable.
     *
     * @return true, if is walkable
     */
    public boolean isWalkable() {

        ItemDefinition definition = this.getDefinition();

        if (definition.getInteractionType() == InteractionType.BED) {
            return true;
        }

        if (this.isGateOpen()) {
            return true;
        }

        if (definition.allowSit()) {
            return true;
        }

        if (definition.isRug()) {
            return true;
        }

        return false;
    }

    /**
     * Is the item open, if it's a gate, teleporter, or one way gate.
     * @return true, if successful
     */
    public boolean isGateOpen() {

        ItemDefinition definition = this.getDefinition();

        if (definition.getInteractionType() == InteractionType.GATE ||
                definition.getInteractionType() == InteractionType.ONEWAYGATE ||
                definition.getInteractionType() == InteractionType.TELEPORT) {

            if (this.getExtraData().equals("1")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * Parse wall item with the arguments given, this should only exist in one place!.
     *
     * @param position the position
     * @return none
     */
    public void parseWallPosition(String position) {

        try {
            String[] xData = position.split(" ")[0].split(",");
            this.side = xData[0].toCharArray()[0];
            this.widthX = Integer.valueOf(xData[1]);
            this.widthY = Integer.valueOf(xData[2]);

            String[] yData = position.split(" ")[1].split(",");
            this.lengthX = Integer.valueOf(yData[0]);
            this.lengthY = Integer.valueOf(yData[1]);

        } catch (NumberFormatException e) {
        	Log.getErrorLogger().error("Error parsing wall item for item Id: {} ", this.id, e);
        }
    }

    /**
     * Gets the variables and generates the needed wall position.
     *
     * @return {@link String} - the wall position string
     */
    public String getWallPosition() {

        if (this.getDefinition().getType() == ItemType.WALL) {
            return ":w=" + this.widthX + "," + this.widthY + " " + "l=" + this.lengthX + "," + this.lengthY + " " + this.side;
        }

        return null;
    }

    /**
     * Update status.
     */
    public void updateStatus() {

        if (this.getRoom() == null) {
            return;
        }
        
        try {
            this.getRoom().send(new MoveItemMessageComposer(this));
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Delete item from database, if the item is a Moodlight/Dimmer
     * that is also deleted.
     */
    public void delete() {
        ItemDao.deleteItem(this.id);
    }


    /**
     * Gets the total height.
     *
     * @return the total height
     */
    public double getTotalHeight() {

        double currentHeight = GameSettings.FURNITURE_OFFSET;

        if (this.getDefinition().getVariableHeight().length > 0) {
            
            if (!Util.isNumber(this.extraData)) {
                this.extraData = "0";
            }

            int variableHeight = Integer.parseInt(this.extraData);
            if (variableHeight >= this.getDefinition().getVariableHeight().length) {
                variableHeight = 0;
                this.extraData = "0";
            }
            
            currentHeight += this.getDefinition().getVariableHeight()[variableHeight];
            
        } else {

            currentHeight += this.position.getZ();

            if (this.getDefinition().allowStack()) {
                currentHeight += this.getDefinition().getStackHeight();
            }
        }

        return currentHeight;
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
     * Gets the definition.
     *
     * @return the definition
     */
    public ItemDefinition getDefinition() {
        return ItemManager.getInstance().getFurnitureById(this.itemId);
    }

    /**
     * Save.
     */
    public void save() {
        ItemDao.saveItem(this);
    }
    
    /**
     * Save.
     */
    public void saveExtraData() {
        ItemDao.saveItemData(this);
    }

    /**
     * Gets the length X.
     *
     * @return the length X
     */
    public int getLengthX() {
        return lengthX;
    }


    /**
     * Gets the length Y.
     *
     * @return the length Y
     */
    public int getLengthY() {
        return lengthY;
    }

    /**
     * Gets the side.
     *
     * @return the side
     */
    public char getSide() {
        return side;
    }


    /**
     * Gets the width X.
     *
     * @return the width X
     */
    public int getWidthX() {
        return widthX;
    }

    /**
     * Gets the width Y.
     *
     * @return the width Y
     */
    public int getWidthY() {
        return widthY;
    }

    /**
     * Gets the owner id.
     *
     * @return the owner id
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the owner name.
     *
     * @return the owner name
     */
    public String getOwnerName() {
        return this.ownerName;
    }

    /**
     * Gets the item id.
     *
     * @return the item id
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Sets the room id.
     *
     * @param id the new room id
     */
    public void setRoomId(int id) {
        this.roomId = id;
    }

    /**
     * Gets the room id.
     *
     * @return the room id
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Gets the room.
     *
     * @return the room
     */
    public Room getRoom() {

        Room room = RoomManager.getInstance().getByRoomId(this.roomId);

        if (room == null) {
            room = RoomDao.getRoom(this.roomId, true);
        }

        return room;
    }

    /**
     * Gets the extra data.
     *
     * @return the extra data
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Sets the extra data.
     *
     * @param extraData the new extra data
     */
    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    /**
     * Gets the position.
     *
     * @return the position
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Gets the tile.
     *
     * @return the tile
     */
    public RoomTile getTile() {

        Room room = this.getRoom();

        if (room == null) {
            return null;
        }

        return room.getMapping().getTile(this.position.getX(), this.position.getY());
    }

    /**
     * Gets the item beneath.
     *
     * @return the item beneath
     */
    public Item getItemBeneath() {
        return itemUnderneath;
    }

    /**
     * Sets the item underneath.
     *
     * @param itemUnderneath the new item underneath
     */
    public void setItemUnderneath(Item itemUnderneath) {
        this.itemUnderneath = itemUnderneath;
    }

    @Override
    public String toString() {
        return "[" + this.id + ", " + this.getDefinition().getItemName() + ", " + this.getDefinition().getInteractionType().name() + ", " + this.position.toString() + "]";
    }
}
