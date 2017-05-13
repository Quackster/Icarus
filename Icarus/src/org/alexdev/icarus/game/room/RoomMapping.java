package org.alexdev.icarus.game.room;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.messages.outgoing.room.items.PlaceItemMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveItemMessageComposer;

public class RoomMapping {

    private Room room;
    private int mapSizeX;
    private int mapSizeY;
    private RoomTile[][] tiles;

    public RoomMapping(Room room) {
        this.room = room;
    }

    public void generateCollisionMaps() {

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

        Item[] items = this.room.getFloorItems();

        for (Item item : items) {

            if (item == null) {
                continue;
            }

            if (item.getDefinition().isWalkable() && !item.getDefinition().isCanSit()) {
                continue;
            }

            double stacked_height = 0;

            if (item.getDefinition().isCanStack()) {
                stacked_height = item.getDefinition().getStackHeight();
            }

            this.checkHighestItem(item, item.getPosition().getX(), item.getPosition().getY());

            RoomTile roomTile = this.getTile(item.getPosition().getX(), item.getPosition().getY());

            if (roomTile == null) {
                continue;
            }

            roomTile.getItems().add(item);
            roomTile.setHeight(roomTile.getHeight() + stacked_height);

            for (AffectedTile tile : item.getAffectedTiles()) {

                if (this.checkHighestItem(item, tile.getX(), tile.getY())) {

                    RoomTile affectedRoomTile = this.getTile(tile.getX(), tile.getY());

                    if (affectedRoomTile != null) {
                        affectedRoomTile.getItems().add(item);
                        affectedRoomTile.setHeight(affectedRoomTile.getHeight() + stacked_height);
                    }
                }
            }
        }
    }


    public boolean isValidStep(Entity entity, Position position, Position tmp, boolean isFinalMove) {

        if (!this.isTileWalkable(entity, position.getX(), position.getY())) {
            return false;
        }

        if (!this.isTileWalkable(entity, tmp.getX(), tmp.getY())) {
            return false;
        }


        return true;
    }

    private boolean checkHighestItem(Item item, int x, int y) {

        if (this.room.getModel().invalidXYCoords(x, y)) {
            return false;
        }

        Item highest_item = this.tiles[x][y].getHighestItem();

        if (highest_item == null) {
            this.tiles[x][y].setHighestItem(item);
        }
        else {
            if (item.getPosition().getZ() > highest_item.getPosition().getZ()) {
                this.tiles[x][y].setHighestItem(item);
            }
        }

        return true;
    }

    public boolean isTileWalkable(Entity entity, int x, int y) {

        if (this.room.getModel().invalidXYCoords(x, y)) {
            return false;
        }

        RoomTile tile = this.tiles[x][y];

        if (tile.hasOverrideLock()) {
            return false;
        }

        if (tile.getEntity() != null) {
            if (tile.getEntity() != entity) {
                return false;
            }
        }

        Item item = tile.getHighestItem();
        boolean tile_valid = (this.room.getModel().isBlocked(x, y) != true);

        if (item != null) {
            if (tile_valid) {
                tile_valid = item.canWalk();
            }
        }

        return tile_valid;
    }

    public void addItem(Item item) {

        item.setRoomId(this.room.getData().getId());

        this.room.getItems().put(item.getId(), item);

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, false);
            this.generateCollisionMaps();
        }

        this.room.send(new PlaceItemMessageComposer(item));

        item.save();
    }

    public void updateItemPosition(Item item, boolean rotation_only) {

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, rotation_only);
            this.generateCollisionMaps();
        }

        item.updateStatus();
        item.save();
    }

    public void removeItem(Item item) {

        item.updateEntities();
        item.setRoomId(0);
        item.save();

        this.room.getItems().remove(item.getId());
        this.room.send(new RemoveItemMessageComposer(item));

        this.generateCollisionMaps();

    }

    private void handleItemAdjustment(Item item, boolean rotation_only) {

        if (rotation_only) {
            for (Item items : this.getTile(item.getPosition().getX(), item.getPosition().getY()).getItems()) {
                if (items != item && items.getPosition().getZ() >= item.getPosition().getZ()) {
                    items.getPosition().setRotation(item.getPosition().getRotation());
                }
            }
        }
        else {
            
            double zOffset = 0.01;

            Item highestItem = this.getHighestItem(item.getPosition().getX(), item.getPosition().getY());

            if (highestItem != null) {
                if (highestItem.getDefinition().isCanStack()) {
                    item.getPosition().setZ(this.getStackHeight(item.getPosition().getX(), item.getPosition().getY()) + zOffset);
                } else {
                    item.getPosition().setZ(highestItem.getPosition().getZ() + zOffset);
                }
            } else {
                item.getPosition().setZ(this.room.getModel().getHeight(item.getPosition().getX(), item.getPosition().getY()) + zOffset);
            }
        }

        item.updateEntities();
    }

    private double getStackHeight(int x, int y) {

        if (this.room.getModel().invalidXYCoords(x, y)) {
            return 0;
        }

        return this.tiles[x][y].getHeight();
    }

    public RoomTile getTile(int x, int y) {

        if (this.room.getModel().invalidXYCoords(x, y)) {
            return null;
        }

        return this.tiles[x][y];
    }

    public Item getHighestItem(int x, int y) {

        if (this.room.getModel().invalidXYCoords(x, y)) {
            return null;
        }

        return this.tiles[x][y].getHighestItem();
    }


}
