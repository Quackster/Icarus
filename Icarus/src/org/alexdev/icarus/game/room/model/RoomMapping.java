package org.alexdev.icarus.game.room.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.alexdev.icarus.dao.mysql.room.MoodlightDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.items.PlaceItemMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveItemMessageComposer;

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
    public void regenerateCollisionMaps() {

        this.mapSizeX = this.room.getModel().getMapSizeX();
        this.mapSizeY = this.room.getModel().getMapSizeY();
        this.tiles = new RoomTile[this.mapSizeX][this.mapSizeY];

        List<Entity> entities = this.room.getEntityManager().getEntities();

        for (Entity entity : entities) {
            this.getTile(entity.getRoomUser().getPosition().getX(), entity.getRoomUser().getPosition().getY()).addEntity(entity);
        }

        List<Item> items = this.room.getItemManager().getFloorItems();

        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                return Double.compare(item1.getPosition().getZ(), item2.getPosition().getZ());
            }
        });

        for (Item item : items) {

            if (item == null) {
                return;
            }

            item.setItemUnderneath(null);

            RoomTile tile = this.getTile(item.getPosition().getX(), item.getPosition().getY());

            if (tile == null) {
                return;
            }

            tile.getItems().add(item);

            if (tile.getHeight() <= item.getTotalHeight()) {

                item.setItemUnderneath(tile.getHighestItem());

                tile.setHeight(item.getTotalHeight());
                tile.setHighestItem(item);

                for (Position affected : item.getAffectedTiles()) {

                    RoomTile affectedTile = this.getTile(affected.getX(), affected.getY());

                    if (tile == null) {
                        continue;
                    }

                    if (affectedTile.getHeight() <= item.getTotalHeight()) {
                        affectedTile.setHeight(item.getTotalHeight());
                        affectedTile.setHighestItem(item);

                    }
                }
            }
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

        if (!this.isTileWalkable(entity, current.getX(), current.getY())) {
            return false;
        }

        if (!this.isTileWalkable(entity, neighbour.getX(), neighbour.getY())) {
            return false;
        }

        RoomTile currentTile = this.getTile(current.getX(), current.getY());

        double currentHeight = this.getTileHeight(current.getX(), current.getY());
        double nextHeight = this.getTileHeight(neighbour.getX(), neighbour.getY());

        if (Position.getHeightDifference(currentHeight, nextHeight) > 1.0) {
            return false;
        }

        if (entity != null) {
            if (!current.isMatch(this.room.getModel().getDoorLocation())) {

                if (!this.isTileWalkable(entity, current.getX(), current.getY())) {
                    return false;
                }

                if (!current.isMatch(entity.getRoomUser().getPosition())) {
                    if (currentTile.getHighestItem() != null) {

                        Item currentItem = currentTile.getHighestItem();

                        if (!isFinalMove) {
                            return currentItem.getDefinition().isWalkable() && !currentItem.getDefinition().allowSit() && currentItem.getDefinition().getInteractionType() != InteractionType.BED;
                        }

                        if (isFinalMove) {
                            return currentItem.canWalk();

                        }
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
    public boolean isTileWalkable(Entity entity, int x, int y) {

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
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
            }

            if (!tile.containsEntity(entity)) {
                return false;
            }
        }

        Item item = tile.getHighestItem();

        if (item != null) {
            if (!item.canWalk()) {
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

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, false);
            this.regenerateCollisionMaps();
        }

        this.room.send(new PlaceItemMessageComposer(item));

        item.updateEntities();
        item.save();
    }

    /**
     * Update item position.
     *
     * @param item the item
     * @param rotation_only the rotation only
     */
    public void updateItemPosition(Item item, boolean rotation_only) {

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, rotation_only);
            this.regenerateCollisionMaps();
        }

        item.updateEntities();
        item.save();
    }

    /**
     * Removes the item, will delete moodlight data if it's a moodlight
     *
     * @param item the item
     */
    public void removeItem(Item item) {

        item.getPosition().setX(0);
        item.getPosition().setY(0);

        if (item.getDefinition().getInteractionType() == InteractionType.DIMMER) {
            if (MoodlightDao.hasMoodlightData(item.getId())) {
                MoodlightDao.deleteMoodlightData(item.getId());
            }

            item.setExtraData("");
        }

        this.room.getItemManager().getItems().remove(item.getId());
        this.regenerateCollisionMaps();

        item.updateEntities();
        item.setRoomId(0);
        item.save();

        this.room.send(new RemoveItemMessageComposer(item));
    }

    /**
     * Handle item adjustment.
     *
     * @param item the item
     * @param rotation_only the rotation only
     */
    private void handleItemAdjustment(Item item, boolean rotation_only) {

        if (rotation_only) {
            for (Item items : this.getTile(item.getPosition().getX(), item.getPosition().getY()).getItems()) {
                if (items != item && items.getPosition().getZ() >= item.getPosition().getZ()) {
                    items.getPosition().setRotation(item.getPosition().getRotation());
                    items.updateStatus();
                }
            }
        } else {
            item.getPosition().setZ(this.getTileHeight(item.getPosition().getX(), item.getPosition().getY()));
        }

        item.updateStatus();
    }

    /**
     * Gets the tile, will create it if it's null (within the valid heightmap size).
     *
     * @param x the x
     * @param y the y
     * @return the tile
     */
    public RoomTile getTile(int x, int y) {

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
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

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
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

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
            return null;
        }

        return this.getTile(x, y).getHighestItem();
    }
}
