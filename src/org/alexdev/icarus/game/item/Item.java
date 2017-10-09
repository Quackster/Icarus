package org.alexdev.icarus.game.item;

import java.util.ArrayList;
import java.util.List;
import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.items.MoveItemMessageComposer;
import org.alexdev.icarus.util.Metadata;
import org.alexdev.icarus.util.Util;

public class Item extends Metadata {

    private int id;
    private int ownerId;
    private String ownerName;
    private int itemId;
    private int roomId;
    private Position position;
    private String extraData;
    private Item itemUnderneath;
    private int teleporterId = 0;
    private int lengthX = 0;
    private int lengthY = 0;
    private char side = 0;
    private int widthX = 0;
    private int widthY = 0;

    public Item(int id, int userId, int itemId, int roomId, String x, String y, double z, int rotation, String extraData) {

        this.id = id;
        this.ownerId = userId;
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
     * Returns the coordinates that this item can possibly affect.
     *
     * @param includeFirstPosition the include first position of the item (by default this function only returns every affected tile except the main position).
     * @return the affected titles
     */
    public List<Position> getAffectedTiles(boolean includeFirstPosition) {

        if (this.getDefinition().getType() == ItemType.WALL) {
            return new ArrayList<>();
        }

        List<Position> affectedTiles = AffectedTile.getAffectedTiles(this.getDefinition().getLength(), this.getDefinition().getWidth(), this.position.getX(), this.position.getY(), this.position.getRotation());

        if (includeFirstPosition) {
            affectedTiles.add(this.position.copy());
        }

        return affectedTiles;
    }


    /**
     * Updates entities who are or were sitting/laying/standing on this furniture.
     * @param previous 
     */
    public void updateEntities() {

        List<Entity> affected_players = new ArrayList<>();

        Room room = this.getRoom();

        if (room == null) {
            return;
        }

        for (Entity entity : this.getRoom().getEntityManager().getEntities()) {

            if (entity.getRoomUser().getCurrentItem() != null) {
                if (entity.getRoomUser().getCurrentItem().getId() == this.id) {

                    // Item doesn't exist within player
                    if (!hasEntityCollision(entity.getRoomUser().getPosition().getX(), entity.getRoomUser().getPosition().getY())) {
                        entity.getRoomUser().setCurrentItem(null);
                    }

                    affected_players.add(entity);

                    // Extra check if a furniture is on a rug... sometimes it bugs up ;)
                } else if (hasEntityCollision(entity.getRoomUser().getPosition().getX(), entity.getRoomUser().getPosition().getY())) {
                    entity.getRoomUser().setCurrentItem(this);
                    affected_players.add(entity);
                }
            }

            // Moved item inside a player
            else if (hasEntityCollision(entity.getRoomUser().getPosition().getX(), entity.getRoomUser().getPosition().getY())) {
                entity.getRoomUser().setCurrentItem(this);
                affected_players.add(entity);
            }
        }

        // Trigger item update for affected players
        for (Entity entity : affected_players) {
            entity.getRoomUser().checkNearbyItem();
        }
    }

    /**
     * Check if specified coordinates collide with the item.
     *
     * @param x the x
     * @param y the y
     * @return {@link boolean} - true if item exits within these coordinates
     */
    private boolean hasEntityCollision(int x, int y) {

        for (Position tile : this.getAffectedTiles(true)) {
            if (tile.getX() == x && tile.getY() == y) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if is walkable, will also check against database.
     *
     * @return true, if is walkable
     */
    public boolean isWalkable() {
        return this.isWalkable(true);
    }

    /**
     * Checks if is walkable.
     *
     * @param includeWalkableWhitelist should we check against the "walkable" definition in database?
     * @return true, if is walkable
     */
    public boolean isWalkable(boolean includeWalkableWhitelist) {

        ItemDefinition definition = this.getDefinition();

        if (definition.getInteractionType() == InteractionType.BED) {
            return true;
        }

        if (definition.getInteractionType() == InteractionType.GATE || 
                definition.getInteractionType() == InteractionType.ONEWAYGATE || 
                definition.getInteractionType() == InteractionType.TELEPORT) {

            if (this.getExtraData().equals("1")) {
                return true;
            } else {
                return false;
            }
        }

        if (definition.allowSit()) {
            return true;
        }

        if (includeWalkableWhitelist && definition.isWalkable()) {
            return true;
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
            String[] x_data = position.split(" ")[0].split(",");
            this.side = x_data[0].toCharArray()[0];
            this.widthX = Integer.valueOf(x_data[1]);
            this.widthY = Integer.valueOf(x_data[2]);

            String[] y_data = position.split(" ")[1].split(",");
            this.lengthX = Integer.valueOf(y_data[0]);
            this.lengthY = Integer.valueOf(y_data[1]);

        } catch (NumberFormatException e) {
            Log.info("Error parsing wall item for item Id: " + this.id);
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
     * Returns a list of items below this current item.
     *
     * @return {@link List} - list of items
     */
    public List<Item> getItemsBeneath() {

        List<Item> items = new ArrayList<>();

        Item item = this;

        while ((item = item.getItemBeneath()) != null) {
            items.add(item);
        }

        return items;
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

        double currentHeight = 0.00;

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
     * Gets the teleporter id.
     *
     * @return the teleporter id
     */
    public int getTeleporterId() {
        return teleporterId;
    }

    /**
     * Sets the teleporter id.
     *
     * @param teleporterId the new teleporter id
     */
    public void setTeleporterId(int teleporterId) {
        this.teleporterId = teleporterId;
    }

    /**
     * Gets the definition.
     *
     * @return the definition
     */
    public ItemDefinition getDefinition() {
        return ItemManager.getFurnitureById(this.itemId);
    }

    /**
     * Save.
     */
    public void save() {
        ItemDao.saveItem(this);
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
     * Sets the length X.
     *
     * @param lengthX the new length X
     */
    public void setLengthX(int lengthX) {
        this.lengthX = lengthX;
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
     * Sets the length Y.
     *
     * @param lengthY the new length Y
     */
    public void setLengthY(int lengthY) {
        this.lengthY = lengthY;
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
     * Sets the side.
     *
     * @param side the new side
     */
    public void setSide(char side) {
        this.side = side;
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
     * Sets the width X.
     *
     * @param widthX the new width X
     */
    public void setWidthX(int widthX) {
        this.widthX = widthX;
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
     * Sets the width Y.
     *
     * @param widthY the new width Y
     */
    public void setWidthY(int widthY) {
        this.widthY = widthY;
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

        Room room = RoomManager.getByRoomId(this.roomId);

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
}
