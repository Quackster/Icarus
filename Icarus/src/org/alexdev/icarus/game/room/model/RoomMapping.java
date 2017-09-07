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
import org.alexdev.icarus.util.Util;

import com.google.common.collect.Lists;

public class RoomMapping {

    private Room room;
    private int mapSizeX;
    private int mapSizeY;
    private RoomTile[][] tiles;
    private List<Position> walkableTiles;

    public RoomMapping(Room room) {
        this.room = room;
        this.walkableTiles = Lists.newArrayList();
    }

    public void regenerateCollisionMaps() {

        this.walkableTiles.clear();
        this.mapSizeX = this.room.getModel().getMapSizeX();
        this.mapSizeY = this.room.getModel().getMapSizeY();

        this.tiles = new RoomTile[this.mapSizeX][this.mapSizeY];

        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                this.tiles[x][y] = new RoomTile(this.room);
                this.tiles[x][y].setHeight(this.room.getModel().getHeight(x, y));
            }
        }

        List<Entity> entities = this.room.getEntities();

        for (Entity entity : entities) {
            this.tiles[entity.getRoomUser().getPosition().getX()][entity.getRoomUser().getPosition().getY()].setEntity(entity);
        }

        List<Item> items = this.room.getFloorItems();

        // Sort items by smallest to largest height/Z coordinate
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
        
        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                if (this.isTileWalkable(null, x, y)) {
                    this.walkableTiles.add(new Position(x, y));
                }                
            }
        }
    }


    public boolean isValidStep(Entity entity, Position current, Position neighbour, boolean isFinalMove) {

        if (!this.isTileWalkable(entity, current.getX(), current.getY())) {
            return false;
        }

        if (!this.isTileWalkable(entity, neighbour.getX(), neighbour.getY())) {
            return false;
        }

        Item currentItem = this.getHighestItem(current.getX(), current.getY());
        //Item nextItem = this.getHighestItem(neighbour.getX(), neighbour.getY());

        double currentHeight = this.getTile(current.getX(), current.getY()).getHeight();
        double nextHeight = this.getTile(neighbour.getX(), neighbour.getY()).getHeight();

        if (currentHeight > nextHeight) {
            if ((currentHeight - nextHeight) > 1.0) {
                return false;
            }
        }

        if (nextHeight > currentHeight) {
            if ((nextHeight - currentHeight) > 1.0) {
                return false;
            }
        }

        if (entity != null) {
            if (!current.isMatch(this.room.getModel().getDoorLocation())) {

                if (!this.isTileWalkable(entity, current.getX(), current.getY())) {
                    return false;
                }

                if (!current.isMatch(entity.getRoomUser().getPosition())) {
                    if (currentItem != null) {
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

    public boolean isTileWalkable(Entity entity, int x, int y) {

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
            return false;
        }

        if (this.room.getModel().isBlocked(x, y)) {
            return false;
        }

        RoomTile tile = this.tiles[x][y];

        if (tile.hasOverrideLock()) {
            return false;
        }

        if (tile.getEntity() != null) {

            if (this.room.getData().isAllowWalkthrough()) {
                return true;
            }

            if (entity != null) {
                if (tile.getEntity() != entity) {
                    return false;
                }
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
    
    public Position getRandomWalkableTile() {
        return this.walkableTiles.get(Util.randomInt(0, this.walkableTiles.size()));
    }

    public void addItem(Item item) {

        item.setRoomId(this.room.getData().getId());

        this.room.getItems().put(item.getId(), item);

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, false);
            this.regenerateCollisionMaps();
        }

        this.room.send(new PlaceItemMessageComposer(item));

        item.updateEntities();
        item.save();
    }

    public void updateItemPosition(Item item, boolean rotation_only) {

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, rotation_only);
            this.regenerateCollisionMaps();
        }

        item.updateEntities();
        item.save();
    }

    public void removeItem(Item item) {

        item.getPosition().setX(-1);
        item.getPosition().setY(-1);

        if (item.getDefinition().getInteractionType() == InteractionType.DIMMER) {
            if (MoodlightDao.hasMoodlightData(item.getId())) {
                MoodlightDao.deleteMoodlightData(item.getId());
            }

            item.setExtraData("");
        }

        this.room.getItems().remove(item.getId());
        this.regenerateCollisionMaps();

        item.updateEntities();
        item.setRoomId(0);
        item.save();

        this.room.send(new RemoveItemMessageComposer(item));
    }

    private void handleItemAdjustment(Item item, boolean rotation_only) {

        if (rotation_only) {
            for (Item items : this.getTile(item.getPosition().getX(), item.getPosition().getY()).getItems()) {
                if (items != item && items.getPosition().getZ() >= item.getPosition().getZ()) {
                    items.getPosition().setRotation(item.getPosition().getRotation());
                    items.updateStatus();
                }
            }
        }
        else {

            double zOffset = 0.01;

            Item highestItem = this.getHighestItem(item.getPosition().getX(), item.getPosition().getY());

            if (highestItem != null) {
                if (highestItem.getDefinition().allowStack()) {
                    item.getPosition().setZ(this.getTileHeight(item.getPosition().getX(), item.getPosition().getY()) + zOffset);
                } else {
                    item.getPosition().setZ(highestItem.getPosition().getZ() + zOffset);
                }
            } else {
                item.getPosition().setZ(this.room.getModel().getHeight(item.getPosition().getX(), item.getPosition().getY()) + zOffset);
            }
        }

        item.updateStatus();
    }

    public double getTileHeight(int x, int y) {

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
            return 0;
        }

        return this.tiles[x][y].getHeight();
    }

    public RoomTile getTile(int x, int y) {

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
            return null;
        }

        return this.tiles[x][y];
    }

    public Item getHighestItem(int x, int y) {

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
            return null;
        }

        return this.tiles[x][y].getHighestItem();
    }
}
