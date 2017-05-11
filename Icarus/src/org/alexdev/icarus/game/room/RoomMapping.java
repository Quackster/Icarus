package org.alexdev.icarus.game.room;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.messages.outgoing.room.items.PlaceItemMessageComposer;

public class RoomMapping {

    private Room room;
    private int mapSizeX;
    private int mapSizeY;
    private RoomTile[][] tiles;

    public RoomMapping(Room room) {
        this.room = room;
    }

    public void regenerateCollisionMaps() {

        this.mapSizeX = this.room.getModel().getMapSizeX();
        this.mapSizeY = this.room.getModel().getMapSizeY();

        this.tiles = new RoomTile[this.mapSizeX][this.mapSizeY];

        for (int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                this.tiles[x][y] = new RoomTile(this.room);
                this.tiles[x][y].setHeight(this.room.getModel().getHeight(x, y));
            }
        }

        Item[] items = this.room.getFloorItems();

        for (Item item : items) {

            if (item == null) {
                continue;
            }

            if (item.getDefinition().isWalkable()) {
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


    public boolean isValidStep(Position position, Position tmp, boolean isFinalMove) {

        if (!this.isTileWalkable(position.getX(), position.getY())) {
            return false;
        }

        if (!this.isTileWalkable(tmp.getX(), tmp.getY())) {
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

    public boolean isTileWalkable(int x, int y) {

        if (this.room.getModel().invalidXYCoords(x, y)) {
            return false;
        }

        RoomTile tile = this.tiles[x][y];

        if (tile.hasOverrideLock()) {
            return false;
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

        this.room.getItems().add(item);

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, false);
            this.regenerateCollisionMaps();
        }

        this.room.send(new PlaceItemMessageComposer(item));

        item.save();
    }

    public void updateItemPosition(Item item, boolean rotation_only) {

        item.setExtraData("");

        if (item.getType() == ItemType.FLOOR) {
            this.handleItemAdjustment(item, rotation_only);
            this.regenerateCollisionMaps();
        }

        item.save();
    }

    public void removeItem(Item item) {

        item.setRoomId(0);
        item.save();

        if (item.getType() == ItemType.FLOOR) {
            item.setExtraData("");
        }

        this.room.getItems().remove(item);
        this.regenerateCollisionMaps();

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

            Item highestItem = this.getHighestItem(item.getPosition().getX(), item.getPosition().getY());

            if (highestItem != null) {
                if (highestItem.getDefinition().isCanStack()) {
                    item.getPosition().setZ(this.getStackHeight(item.getPosition().getX(), item.getPosition().getY()));
                } else {
                    item.getPosition().setZ(highestItem.getPosition().getZ() + 0.01);
                }
            } else {
                item.getPosition().setZ(this.room.getModel().getHeight(item.getPosition().getX(), item.getPosition().getY()));
            }

            // Don't make rugs stackable on top of other objects

            /*    item.getPosition().setZ(this.room.getModel().getHeight(item.getPosition().getX(), item.getPosition().getY()));
            } else {
                item.getPosition().setZ(this.getStackHeight(item.getPosition().getX(), item.getPosition().getY()));

            }*/
        }
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
