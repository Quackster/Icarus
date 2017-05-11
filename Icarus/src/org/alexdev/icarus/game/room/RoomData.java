package org.alexdev.icarus.game.room;

import java.util.ArrayList;
import java.util.List;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomData {

	private int id;
	private RoomType roomType;
	private int ownerId;
	private String ownerName;
	private String name;
	private RoomState state;
	private String password;
	private String thumbnail;
	private int usersNow;
	private int usersMax;
	private String description;
	private int tradeState;
	private int score;
	private int category;
	private int groupId;
	private String model;
	private String wall;
	private String floor;
	private String landscape;
	private boolean allowPets;
	private boolean allowPetsEat;
	private boolean allowWalkthrough;
	private boolean hideWall;
	private int wallThickness;
	private int floorThickness;
	private String[] tags;
	private List<Integer> rights;
	private Room room;
	private int chatType;
	private int chatBalloon;
	private int chatSpeed;
	private int chatMaxDistance;
	private int chatFloodProtection;
	private int whoCanMute;
	private int whoCanKick;
	private int whoCanBan;

	
	public RoomData(Room room) {
		
		this.room = room;
		this.rights = new ArrayList<Integer>();
	}
	
	public void fill(int id, RoomType type, int ownerId, String ownerName, String name, int state, String password, int usersNow, int usersMax,
			String description, int tradeState, int score, int category, int groupId, String model, String wall,
			String floor, String landscape, boolean allowPets, boolean allowPetsEat, boolean allowWalkthrough,
			boolean hideWall, int wallThickness, int floorThickness, String tagFormat, int chatType, int chatBalloon, int chatSpeed,
			int chatMaxDistance, int chatFloodProtection, int whoCanMute, int whoCanKick, int whoCanBan, String thumbnail) {

		this.id = id;
		this.roomType = type;
		this.ownerId = ownerId;
		this.ownerName = ownerName;
		this.name = name;
		this.state = RoomState.getState(state);
		this.password = password;
		this.usersNow = usersNow;
		this.usersMax = usersMax;
		this.description = description;
		this.tradeState = tradeState;
		this.score = score;
		this.category = category;
		this.groupId = groupId;
		this.model = model;
		this.wall = wall;
		this.floor = floor;
		this.landscape = landscape;
		this.allowPets = allowPets;
		this.allowPetsEat = allowPetsEat;
		this.allowWalkthrough = allowWalkthrough;
		this.hideWall = hideWall;
		this.wallThickness = wallThickness;
		this.floorThickness = floorThickness;
		this.setTags(tagFormat.split(","));
		this.rights = RoomDao.getRoomRights(this.id);
		this.chatType = chatType;
		this.chatSpeed = chatSpeed;
		this.chatMaxDistance = chatMaxDistance;
		this.chatFloodProtection = chatFloodProtection;
		this.whoCanMute = whoCanMute;
		this.whoCanKick = whoCanKick;
		this.whoCanBan = whoCanBan;
		this.thumbnail = thumbnail;
	}
	
	public void serialise(Response response) {
		this.serialise(response, false);
	}

	public void serialise(Response response, boolean enterRoom) {
		
		response.writeInt(id);
		response.writeString(this.name);
		response.writeInt(this.ownerId);
		response.writeString(this.ownerName);
		response.writeInt(this.state.getStateCode());
		response.writeInt(this.usersNow);
		response.writeInt(this.usersMax);
		response.writeString(this.description);
		response.writeInt(this.tradeState);
		response.writeInt(this.score);
		response.writeInt(0); // Ranking
		response.writeInt(this.category);
		response.writeInt(this.tags.length); //TagCount

		for (String tag : this.tags) {
			response.writeString(tag);
		}
		
		int enumType = enterRoom ? 32 : 0;
		
		// if has event
		//enumType += 4;

		if (this.thumbnail != null) {
			if (this.thumbnail.length() > 0) {
				enumType += 1;
			}
		}
		
		if (this.roomType == RoomType.PRIVATE) {
			enumType += 8;
		}

		if (this.allowPets) { 
			enumType += 16;
		}

		response.writeInt(enumType);
		
		if (this.thumbnail != null) {
			if (this.thumbnail.length() > 0) {
				response.writeString(this.thumbnail);
			}
		}
		
		//response.appendString("Hello");
        //response.appendString("xd lolz");
        //response.appendInt32(100);
        

	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomState getState() {
		return state;
	}

	public void setState(int state) {
		this.state = RoomState.getState(state);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTradeState() {
		return tradeState;
	}

	public void setTradeState(int tradeState) {
		this.tradeState = tradeState;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getWall() {
		return wall;
	}

	public void setWall(String wall) {
		this.wall = wall;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getLandscape() {
		return landscape;
	}

	public void setLandscape(String landscape) {
		this.landscape = landscape;
	}

	public boolean isAllowPets() {
		return allowPets;
	}

	public void setAllowPets(boolean allowPets) {
		this.allowPets = allowPets;
	}

	public boolean isAllowPetsEat() {
		return allowPetsEat;
	}

	public void setAllowPetsEat(boolean allowPetsEat) {
		this.allowPetsEat = allowPetsEat;
	}

	public boolean isAllowWalkthrough() {
		return allowWalkthrough;
	}

	public void setAllowWalkthrough(boolean allowWalkthrough) {
		this.allowWalkthrough = allowWalkthrough;
	}

	public boolean isHideWall() {
		return hideWall;
	}

	public void setHideWall(boolean hideWall) {
		this.hideWall = hideWall;
	}

	public int getWallThickness() {
		return wallThickness;
	}


	public void setWallThickness(int wallThickness) {
		this.wallThickness = wallThickness;
	}

	public Integer getWallHeight() {
		return -1;
	}

	public int getFloorThickness() {
		return floorThickness;
	}

	public void setFloorThickness(int floorThickness) {
		this.floorThickness = floorThickness;
	}

	public int getId() {
		return id;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setUsersMax(int usersMax) {
		this.usersMax = usersMax;
	}

	public int getUsersMax() {
		return this.usersMax;
	}

	public int getUsersNow() {
		return this.room.getUsers().size();
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public RoomModel getModel() {
		return RoomDao.getModel(this.model);
	}
	
	public List<Integer> getRights() {
		return rights;
	}

	public int getChatType() {
		return chatType;
	}

	public void setChatType(int chatType) {
		this.chatType = chatType;
	}

	public int getChatBalloon() {
		return chatBalloon;
	}

	public void setChatBalloon(int chatBalloon) {
		this.chatBalloon = chatBalloon;
	}

	public int getChatSpeed() {
		return chatSpeed;
	}

	public void setChatSpeed(int chatSpeed) {
		this.chatSpeed = chatSpeed;
	}

	public int getChatMaxDistance() {
		return chatMaxDistance;
	}

	public void setChatMaxDistance(int chatMaxDistance) {
		this.chatMaxDistance = chatMaxDistance;
	}

	public int getChatFloodProtection() {
		return chatFloodProtection;
	}

	public void setChatFloodProtection(int chatFloodProtection) {
		this.chatFloodProtection = chatFloodProtection;
	}

	public int getWhoCanMute() {
		return whoCanMute;
	}

	public void setWhoCanMute(int whoCanMute) {
		
		if (whoCanMute > 2) {
			return;
		}
		
		this.whoCanMute = whoCanMute;
	}

	public int getWhoCanKick() {
		return whoCanKick;
	}

	public void setWhoCanKick(int whoCanKick) {
		
		if (whoCanKick > 2) {
			return;
		}
		
		this.whoCanKick = whoCanKick;
	}

	public int getWhoCanBan() {
		return whoCanBan;
	}

	public void setWhoCanBan(int whoCanBan) {
		
		if (whoCanBan > 2) {
			return;
		}
		
		this.whoCanBan = whoCanBan;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
