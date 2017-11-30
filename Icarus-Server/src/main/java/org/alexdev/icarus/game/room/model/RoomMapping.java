package org.alexdev.icarus.game.room.model;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.items.PlaceItemMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveItemMessageComposer;

import java.util.List;

public class RoomMapping {

    private int mapSizeX;
    private int mapSizeY;

    private Room room;
    private RoomTile[][] tiles;

    public RoomMapping(Room room) {
        this.room = room;
    }

    /**
     * Regenerate collision maps.
     */
    public void regenerateCollisionMaps(boolean refreshItemList) {
        this.mapSizeX = this.room.getModel().getMapSizeX();
        this.mapSizeY = this.room.getModel().getMapSizeY();
        this.tiles = new RoomTile[this.mapSizeX][this.mapSizeY];

        for (Entity entity : this.room.getEntityManager().getEntities()) {
            this.getTile(entity.getRoomUser().getPosition().getX(), entity.getRoomUser().getPosition().getY()).addEntity(entity);
        }

        List<Item> items = this.room.getItemManager().getFloorItems();
        items.sort((item1, item2) -> Double.compare(item1.getPosition().getZ(), item2.getPosition().getZ()));

        for (Item item : items) {

            if (item == null) {
                return;
            }

            item.setItemUnderneath(null);

            RoomTile tile = item.getTile();

            if (tile == null) {
                return;
            }

            tile.getItems().add(item);

            if (tile.getHeight() < item.getTotalHeight()) {
                item.setItemUnderneath(tile.getHighestItem());
                tile.setHeight(item.getTotalHeight());
                tile.setHighestItem(item);

                for (Position affected : AffectedTile.getAffectedTiles(item.getPosition(), item.getDefinition())) {

                    RoomTile affectedTile = this.getTile(affected.getX(), affected.getY());

                    if (tile == null) {
                        continue;
                    }

                    affectedTile.setHeight(item.getTotalHeight());
                    affectedTile.setHighestItem(item);
                }
            }
        }

        if (refreshItemList) {
            this.room.getItemManager().refreshHasRollers();
        }
    }

    /**
     * Checks if is valid step.
     *
     * @param entity the entity
     * @param current the current
     * @param neighbour the neighbour
     * @param isFinalMove the is final move
     * @return true, if is valid step
     */
    public boolean isValidStep(Entity entity, Position current, Position neighbour, boolean isFinalMove) {
        if (!this.isTileWalkable(current.getX(), current.getY(), entity)) {
            return false;
        }

        if (!this.isTileWalkable(neighbour.getX(), neighbour.getY(), entity)) {
            return false;
        }

        RoomTile currentTile = this.getTile(current.getX(), current.getY());
        RoomTile nextTile = this.getTile(neighbour.getX(), neighbour.getY());

        double currentHeight = currentTile.getHeight();
        double nextHeight = nextTile.getHeight();

        if (Position.getHeightDifference(currentHeight, nextHeight) > 1.0) {
            return false;
        }

        if (!current.equals(this.room.getModel().getDoorLocation())) {
            if (!this.isTileWalkable(current.getX(), current.getY(), entity)) {
                return false;
            }

            if (!current.equals(entity.getRoomUser().getPosition())) {
                if (currentTile.getHighestItem() != null) {

                    Item currentItem = currentTile.getHighestItem();

                    if (!isFinalMove) {
                        return currentItem.getDefinition().isRug() || currentItem.isGateOpen();
                    } else {
                        return currentItem.isWalkable();
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks if is tile walkable.
     *
     * @param entity the entity
     * @param x the x
     * @param y the y
     * @return true, if is tile walkable
     */
    public boolean isTileWalkable(int x, int y, Entity entity) {
        if (this.room.getModel().isOutsideBounds(x, y)) {
            return false;
        }

        if (this.room.getModel().isBlocked(x, y)) {
            return false;
        }

        RoomTile tile =  this.getTile(x, y);

        if (tile.hasOverrideLock()) {
            return false;
        }

        if (tile.getEntities().size() > 0) {
            if (this.room.getData().isAllowWalkthrough()) {
                return true;

            } else {
                if (!tile.containsEntity(entity)) {
                    return false;
                } else {
                    return true;
                }
            }
        }

        Item item = tile.getHighestItem();

        if (item != null) {
            if (!item.isWalkable()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Adds the item.
     *
     * @param item the item
     */
    public void addItem(Item item) {
        item.setRoomId(this.room.getData().getId());

        this.room.getItemManager().getItems().put(item.getId(), item);
        this.room.send(new PlaceItemMessageComposer(item));

        if (item.getDefinition().getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, false);
            this.regenerateCollisionMaps(true);
        }

        item.updateEntities(null);
        item.save();
    }

    /**
     * Update item position.
     *
     * @param item the item
     * @param isRotation the rotation only
     */
    public void moveItem(Item item, boolean isRotation, Position previous) {
        this.room.send(new PlaceItemMessageComposer(item));

        if (item.getDefinition().getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, isRotation);
            this.regenerateCollisionMaps(false);
        }

        item.updateEntities(previous);
        item.updateStatus();
        item.save();
    }

    /**
     * Removes the item, will delete moodlight data if it's a moodlight
     *
     * @param item the item
     */
    public void removeItem(Item item) {
        this.room.getItemManager().getItems().remove(item.getId());
        this.regenerateCollisionMaps(true);

        if (item.getDefinition().getInteractionType() == InteractionType.DIMMER) {
            item.setExtraData("");
            item.saveExtraData();
        }

        item.updateEntities(null);
        item.setRoomId(0);
        item.getPosition().setX(0);
        item.getPosition().setY(0);
        item.getPosition().setZ(0);
        item.save();

        this.room.send(new RemoveItemMessageComposer(item));
    }

    /**
     * Handle item adjustment.
     *
     * @param moveItem the item
     * @param rotation the rotation only
     */
    private void handleItemAdjustment(Item moveItem, boolean rotation) {
        if (rotation) {
            for (Item item : this.getTile(moveItem.getPosition().getX(), moveItem.getPosition().getY()).getItems()) {

                if (item.getId() == moveItem.getId()) {
                    continue;
                }

                if (item.getPosition().getZ() < moveItem.getPosition().getZ()) {
                    continue;
                }

                item.getPosition().setRotation(moveItem.getPosition().getRotation());
                item.updateStatus();
            }
        } else {
            moveItem.getPosition().setZ(this.getTileHeight(moveItem.getPosition().getX(), moveItem.getPosition().getY()));
        }
    }

    /**
     * Gets the tile, will create it if it's null (within the valid heightmap size).
     *
     * @param x the x
     * @param y the y
     * @return the tile
     */
    public RoomTile getTile(int x, int y) {
        if (this.room.getModel().isOutsideBounds(x, y)) {
            return null;
        }

        RoomTile tile = this.tiles[x][y];

        if (tile == null) {
            this.tiles[x][y] = new RoomTile(this.room, x, y);
        }

        return this.tiles[x][y];
    }

    /**
     * Gets the tile height.
     *
     * @param x the x
     * @param y the y
     * @return the tile height
     */
    public double getTileHeight(int x, int y) {
        if (this.room.getModel().isOutsideBounds(x, y)) {
            return 0;
        }

        return this.getTile(x, y).getHeight();
    }

    /**
     * Gets the highest item.
     *
     * @param x the x
     * @param y the y
     * @return the highest item
     */
    public Item getHighestItem(int x, int y) {
        if (this.room.getModel().isOutsideBounds(x, y)) {
            return null;
        }

        return this.getTile(x, y).getHighestItem();
    }
}
