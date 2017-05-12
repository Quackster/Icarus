package org.alexdev.icarus.game.item;

import java.util.List;

import org.alexdev.icarus.dao.mysql.ItemDao;
import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.game.furniture.FurnitureManager;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.serialise.FloorItemSerialise;
import org.alexdev.icarus.game.item.serialise.WallItemSerialise;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.items.MoveItemMessageComposer;

import com.google.common.collect.Lists;

public class Item {

    private int id;
    private int userId;
    private int itemId;
    private int roomId;
    private Position position;
    private String extraData;
    private ItemType type;
    private ItemSerialise serializer;

    /**
     * Wall position variables
     */
    private int lengthX = 0;
    private int lengthY = 0;
    private char side = 0;
    private int widthX = 0;
    private int widthY = 0;

    public Item(long id, int userId, int itemId, int roomId, String x, String y, double z, int rotation, String extraData) {
        this.id = (int)id;
        this.userId = userId;
        this.itemId = itemId;
        this.roomId = roomId;
        this.extraData = extraData;

        if (this.getDefinition().getType().equals("i")) {
            this.type = ItemType.WALL;
        } else if (this.getDefinition().getType().equals("s")) {
            this.type = ItemType.FLOOR;
        } else {
            this.type = ItemType.OTHER;
        }

        this.position = new Position();

        if (this.type == ItemType.FLOOR) {
            this.position.setX(Integer.parseInt(x));
            this.position.setY(Integer.parseInt(y));
            this.position.setZ(z);
            this.position.setRotation(rotation);
            this.serializer = new FloorItemSerialise(this);
        }
        
        if (this.type == ItemType.WALL) {
            if (this.roomId > 0) {
                Log.println("PARSE WALL ITEM");
                this.parseWallPosition(x + " " + y);
            }
            
            this.serializer = new WallItemSerialise(this);
        }
    }

    /**
     * Returns the coordinates that this item can possibly affect, such as
     * a table covering 2x2 squares
     * 
     * @return {@link List} - of {@link AffectedTile}'s
     */
    public List<AffectedTile> getAffectedTiles() {

        if (this.type == ItemType.WALL) {
            return Lists.newArrayList();
        }

        return AffectedTile.getAffectedTilesAt(this.getDefinition().getLength(), this.getDefinition().getWidth(), this.position.getX(), this.position.getY(), this.position.getRotation());
    }

    /**
     * Is this item walkable or not
     * 
     * @return {@link boolean} - true if walkable
     */
    public boolean canWalk() {

        ItemDefinition definition = this.getDefinition();

        if (definition.isCanSit()) {
            return true;
        }

        if (definition.getInteractionType() == InteractionType.BED) {
            return true;
        }

        return false;
    }
    
    /**
        Parse wall item with the arguments given, this should only exist in one place!
    
        @param Wall position (left/right,width_x, width_y length_x, length_y) eg (r,3,6 2,7)
        @return none
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
            Log.println("Error parsing wall item for item ID: " + this.id);
        }
    }
    
    /**
     * Gets the variables and generates the needed wall position
     */
    public String getWallPosition() {
    
        if (this.type == ItemType.WALL) {
            return ":w=" + this.widthX + "," + this.widthY + " " + "l=" + this.lengthX + "," + this.lengthY + " " + this.side;
        }
    
        return null;
    }
    
    public void updateStatus() {
        this.getRoom().send(new MoveItemMessageComposer(this));
    }

    public int getId() {
        return id;
    }

    public ItemDefinition getDefinition() {
        return FurnitureManager.getFurnitureById(this.itemId);
    }

    public void save() {
        ItemDao.saveItem(this);
    }

    public void delete() {
        ItemDao.deleteItem(this.id);
    }

    public int getLengthX() {
        return lengthX;
    }

    public void setLengthX(int lengthX) {
        this.lengthX = lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    public void setLengthY(int lengthY) {
        this.lengthY = lengthY;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public int getWidthX() {
        return widthX;
    }

    public void setWidthX(int widthX) {
        this.widthX = widthX;
    }

    public int getWidthY() {
        return widthY;
    }

    public void setWidthY(int widthY) {
        this.widthY = widthY;
    }

    public int getOwnerId() {
        return userId;
    }

    public PlayerDetails getOwnerData() {
        return PlayerManager.getPlayerData(this.userId);
    }

    public int getItemId() {
        return itemId;
    }

    public void setRoomId(int id) {
        this.roomId = id;
    }

    public int getRoomId() {
        return roomId;
    }
    
    public Room getRoom() {
        return RoomManager.find(this.roomId);
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }


    public ItemType getType() {
        return type;
    }

    public Position getPosition() {
        return this.position;
    }

    public ItemSerialise getSerializer() {
        return serializer;
    }

    public void setSerializer(ItemSerialise serializer) {
        this.serializer = serializer;
    }


}
