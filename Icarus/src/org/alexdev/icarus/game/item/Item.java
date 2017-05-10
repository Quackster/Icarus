package org.alexdev.icarus.game.item;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.dao.mysql.ItemDao;
import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.game.furniture.Furniture;
import org.alexdev.icarus.game.furniture.FurnitureManager;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.server.messages.AbstractResponse;
import org.alexdev.icarus.util.GameSettings;

public class Item {

	private int gameId;
	private long databaseId;
	private int userId;
	private int itemId;
	private int roomId;
	private int x;
	private int y;
	private double z;
	private int rotation;
	private String extraData;
	private ItemType type;

	private int lengthX = 0;
	private int lengthY = 0;

	private char side = 0;
	private int widthX = 0;
	private int widthY = 0;

	public void fill(long databaseId, int userId, int itemId, int roomId, String x, String y, double z, int rotation, String extraData) {
		this.databaseId = databaseId;
		this.gameId = GameSettings.ITEM_ID_COUNTER++;
		this.userId = userId;
		this.itemId = itemId;
		this.roomId = roomId;
		this.rotation = rotation;
		this.extraData = extraData;
		this.type = this.getData().getType().equals("i") ? ItemType.WALL : ItemType.FLOOR;

		if (this.type == ItemType.FLOOR) {

			this.x = Integer.parseInt(x);
			this.y = Integer.parseInt(y);

		} else {

			try {
				String[] xData = x.split(",");

				this.side = xData[0].toCharArray()[0];
				this.widthX = Integer.parseInt(xData[1]);
				this.widthY = Integer.parseInt(xData[2]);

				String[] yData = y.split(",");

				this.lengthX = Integer.parseInt(yData[0]);
				this.lengthY = Integer.parseInt(yData[1]);
				
			} catch (Exception e) {
				
				this.side = ' ';
				this.widthX = 0;
				this.widthY = 0;
				this.lengthX = 0;
				this.lengthY = 0;
			}
		}

		this.z = z;
	}

	public void save() {
		ItemDao.saveItem(this);
	}
	
	public List<AffectedTile> getAffectedTiles() {
		return AffectedTile.getAffectedTilesAt(this.getData().getLength(), this.getData().getWidth(), this.x, this.y, this.rotation);
	}

	public void serialise(AbstractResponse message) {
		if (this.type == ItemType.WALL) {

			message.appendString(this.gameId + "");
			message.appendInt32(this.getData().getSpriteId());
			message.appendString(this.getWallPosition());

			if (this.getData().getInteractionType() == InteractionType.POSTIT) {
				message.appendString(this.extraData.split(" ")[0]);
			} else {
				message.appendString(this.extraData);
			}

			message.appendInt32(-1);
			message.appendInt32(this.getData().getInteractionType() == InteractionType.DEFAULT ? 0 : 1);
			message.appendInt32(this.userId);

		}

		if (this.type == ItemType.FLOOR) {

			message.appendInt32(this.gameId);
			message.appendInt32(this.getData().getSpriteId());
			message.appendInt32(this.x);
			message.appendInt32(this.y);
			message.appendInt32(this.rotation);
			message.appendString("" + this.z);
			message.appendString("" + this.z);

			if (this.getData().getInteractionType() == InteractionType.YOUTUBETV) {

				message.appendInt32(0);
				message.appendInt32(1);
				message.appendInt32(1);
				message.appendString("THUMBNAIL_URL");
				message.appendString("/deliver/" + "");
			} else if (this.getData().getInteractionType() == InteractionType.BADGE_DISPLAY) {

				message.appendInt32(0);
				message.appendInt32(2);
				message.appendInt32(4);

				if (this.extraData.length() > 0) {

					message.appendString("0"); // extradata check

					for (int i = 0; i <= this.extraData.split(Character.toString((char)9)).length - 1; i++)
						message.appendString(this.extraData.split(Character.toString((char)9))[i]);
				} else {
					message.appendInt32(0);
				}

			} else if (this.getData().getInteractionType() == InteractionType.BG_COLORBACKGROUND) {

				message.appendInt32(1); // is ads
				message.appendInt32(5); //type
				message.appendInt32(4);

				message.appendInt32(0); // online?
				message.appendInt32(0);
				message.appendInt32(0);
				message.appendInt32(0);
			} else if (this.getData().getInteractionType() == InteractionType.MANNEQUIN) {

				String[] Extradatas = this.extraData.split(";");

				if (this.extraData.contains(";") && Extradatas.length >= 3)
				{
					message.appendInt32(1);
					message.appendInt32(1);
					message.appendInt32(3);

					message.appendString("GENDER");
					message.appendString(Extradatas[0]);
					message.appendString("FIGURE");
					message.appendString(Extradatas[1]);
					message.appendString("OUTFIT_NAME");
					message.appendString(Extradatas[2]);
				}
				else
				{
					message.appendInt32(1);
					message.appendInt32(1);
					message.appendInt32(3);

					message.appendString("GENDER");
					message.appendString("m");
					message.appendString("FIGURE");
					message.appendString("");
					message.appendString("OUTFIT_NAME");
					message.appendString("");
				}
			} else {
				message.appendInt32((this.getData().getInteractionType() == InteractionType.DEFAULT) ? 0 : 1);
				message.appendInt32(0);
				message.appendString(this.extraData);
			}

			message.appendInt32(-1); // secondsToExpiration
			message.appendInt32(this.getData().getInteractionType() != InteractionType.DEFAULT ? 1 : 0);
			message.appendInt32(this.getOwnerId());
		}
	}

	// Different ids so the database can support 64bit integer of items
	// When emulator is loaded, the item ids for the client are generated when client connects 
	// Credits to Leon to support 9,223,372,036,854,775,80 items :))

	public int getGameId() {
		return gameId;
	}

	public long getDatabaseId() {
		return databaseId;
	}

	public Furniture getData() {
		return FurnitureManager.getFurnitureById(this.itemId);
	}

	public void delete() {
		ItemDao.deleteItem(this.databaseId);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
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

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public String getWallPosition() {
		if (this.type == ItemType.WALL) {
			return ":w=" + this.widthX + "," + this.widthY + " " + "l=" + this.lengthX + "," + this.lengthY + " " + this.side;
		}

		return null;
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

	public String getExtraData() {
		return extraData;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}


	public ItemType getType() {
		return type;
	}



}
