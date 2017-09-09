package org.alexdev.icarus.game.room;

import java.util.concurrent.atomic.AtomicInteger;

import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomData {

    private int id;
    private RoomType roomType;
    private int ownerID;
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
    private int groupID;
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
    }
    
    public void fill(int id, RoomType type, int ownerID, String ownerName, String name, int state, String password, int usersNow, int usersMax,
            String description, int tradeState, int score, int category, int groupID, String model, String wall,
            String floor, String landscape, boolean allowPets, boolean allowPetsEat, boolean allowWalkthrough,
            boolean hideWall, int wallThickness, int floorThickness, String tagFormat, int chatType, int chatBalloon, int chatSpeed,
            int chatMaxDistance, int chatFloodProtection, int whoCanMute, int whoCanKick, int whoCanBan, String thumbnail) {

        this.id = id;
        this.roomType = type;
        this.ownerID = ownerID;
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
        this.groupID = groupID;
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
        this.chatType = chatType;
        this.chatSpeed = chatSpeed;
        this.chatMaxDistance = chatMaxDistance;
        this.chatFloodProtection = chatFloodProtection;
        this.whoCanMute = whoCanMute;
        this.whoCanKick = whoCanKick;
        this.whoCanBan = whoCanBan;
        this.thumbnail = thumbnail;
    }
    
    public void serialise(Response response, boolean enterRoom) {
        
        response.writeInt(id);
        response.writeString(this.name);
        response.writeInt(this.ownerID);
        response.writeString(this.ownerName);
        response.writeInt(this.state.getStateCode());
        response.writeInt(this.usersNow);
        response.writeInt(this.usersMax);
        response.writeString(this.description);
        response.writeInt(this.tradeState);
        response.writeInt(this.score);
        response.writeInt(0);
        response.writeInt(this.category);
        response.writeInt(this.tags.length);

        for (String tag : this.tags) {
            response.writeString(tag);
        }
        
        AtomicInteger roomListingType = new AtomicInteger(enterRoom ? 32 : 0);
        
        if (this.thumbnail != null) {
            if (this.thumbnail.length() > 0) {
                roomListingType.getAndAdd(1);
            }
        }
        
        if (this.roomType == RoomType.PRIVATE) {
            roomListingType.getAndAdd(8);
        }

        if (this.allowPets) { 
            roomListingType.getAndAdd(16);
        }
        
        if (this.room.getPromotion() != null) {
            roomListingType.getAndAdd(4);
        }
        
        /*if (this.room.getGroup() != null) {
            enumType += 2;
        }*/

        response.writeInt(roomListingType.get());
        
        if (this.thumbnail != null) {
            if (this.thumbnail.length() > 0) {
                response.writeString(this.thumbnail);
            }
        }
        
        if (this.room.getPromotion() != null) {
            response.writeString(this.room.getPromotion().getPromotionName());
            response.writeString(this.room.getPromotion().getPromotionDescription());
            response.writeInt(this.room.getPromotion().getPromotionMinutesLeft().get());
        }
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

    public int getUsersNow() {
        return usersNow;
    }

    public void setUsersNow(int usersNow) {
        this.usersNow = usersNow;
    }
    
    public void updateUsersNow() {
        this.usersNow = this.room.getPlayers().size();
    }
    public int getUsersMax() {
        return usersMax;
    }

    public void setUsersMax(int usersMax) {
        this.usersMax = usersMax;
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

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
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

    public boolean hasHiddenWall() {
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

    public int getID() {
        return id;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public String getOwnerName() {
        return ownerName;
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

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return this.model;
    }

    public int getBubbleMode() {
        return chatType;
    }

    public void setBubbleMode(int chatType) {
        this.chatType = chatType;
    }

    public int getBubbleType() {
        return chatBalloon;
    }

    public void setBubbleType(int chatBalloon) {
        this.chatBalloon = chatBalloon;
    }

    public int getBubbleScroll() {
        return chatSpeed;
    }

    public void setBubbleScroll(int chatSpeed) {
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

    public void setWallHeight(int wallHeight) {
        // TODO Auto-generated method stub
        
    }
}
