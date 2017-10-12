package org.alexdev.icarus.game.room;

import org.alexdev.icarus.game.room.enums.RoomState;
import org.alexdev.icarus.game.room.enums.RoomType;
import org.alexdev.icarus.util.metadata.Metadata;

public class RoomData extends Metadata {

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
    private int chatType;
    private int chatBalloon;
    private int chatSpeed;
    private int chatMaxDistance;
    private int chatFloodProtection;
    private int whoCanMute;
    private int whoCanKick;
    private int whoCanBan;
    
    /**
     * Fills the room data.
     *
     * @param id the id
     * @param type the type
     * @param ownerId the owner id
     * @param ownerName the owner name
     * @param name the name
     * @param state the state
     * @param password the password
     * @param usersNow the users now
     * @param usersMax the users max
     * @param description the description
     * @param tradeState the trade state
     * @param score the score
     * @param category the category
     * @param groupId the group id
     * @param model the model
     * @param wall the wall
     * @param floor the floor
     * @param landscape the landscape
     * @param allowPets the allow pets
     * @param allowPetsEat the allow pets eat
     * @param allowWalkthrough the allow walkthrough
     * @param hideWall the hide wall
     * @param wallThickness the wall thickness
     * @param floorThickness the floor thickness
     * @param tagFormat the tag format
     * @param chatType the chat type
     * @param chatBalloon the chat balloon
     * @param chatSpeed the chat speed
     * @param chatMaxDistance the chat max distance
     * @param chatFloodProtection the chat flood protection
     * @param whoCanMute the who can mute
     * @param whoCanKick the who can kick
     * @param whoCanBan the who can ban
     * @param thumbnail the thumbnail
     */
    public RoomData(int id, RoomType type, int ownerId, String ownerName, String name, String state, String password, int usersNow, int usersMax,
            String description, int tradeState, int score, int category, int groupId, String model, String wall,
            String floor, String landscape, boolean allowPets, boolean allowPetsEat, boolean allowWalkthrough,
            boolean hideWall, int wallThickness, int floorThickness, String tagFormat, int chatType, int chatBalloon, int chatSpeed,
            int chatMaxDistance, int chatFloodProtection, int whoCanMute, int whoCanKick, int whoCanBan, String thumbnail) {

        this.id = id;
        this.roomType = type;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.name = name;
        this.state = RoomState.valueOf(state);
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
        this.chatType = chatType;
        this.chatSpeed = chatSpeed;
        this.chatMaxDistance = chatMaxDistance;
        this.chatFloodProtection = chatFloodProtection;
        this.whoCanMute = whoCanMute;
        this.whoCanKick = whoCanKick;
        this.whoCanBan = whoCanBan;
        this.thumbnail = thumbnail;
    }
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the state.
     *
     * @return the state
     */
    public RoomState getState() {
        return state;
    }

    /**
     * Sets the state.
     *
     * @param state the new state
     */
    public void setState(int state) {
        this.state = RoomState.getState(state);
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the users now.
     *
     * @return the users now
     */
    public int getUsersNow() {
        return usersNow;
    }

    /**
     * Sets the users now.
     *
     * @param usersNow the new users now
     */
    public void setUsersNow(int usersNow) {
        this.usersNow = usersNow;
    }
    
    /**
     * Gets the users max.
     *
     * @return the users max
     */
    public int getUsersMax() {
        return usersMax;
    }

    /**
     * Sets the users max.
     *
     * @param usersMax the new users max
     */
    public void setUsersMax(int usersMax) {
        this.usersMax = usersMax;
    }

    /**
     * Gets the trade state.
     *
     * @return the trade state
     */
    public int getTradeState() {
        return tradeState;
    }

    /**
     * Sets the trade state.
     *
     * @param tradeState the new trade state
     */
    public void setTradeState(int tradeState) {
        this.tradeState = tradeState;
    }

    /**
     * Gets the score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score.
     *
     * @param score the new score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the category.
     *
     * @return the category
     */
    public int getCategory() {
        return category;
    }

    /**
     * Sets the category.
     *
     * @param category the new category
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * Gets the group id.
     *
     * @return the group id
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets the group id.
     *
     * @param groupId the new group id
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets the wall.
     *
     * @return the wall
     */
    public String getWall() {
        
        if (this.wall.isEmpty()) {
            return "0";
        }
        
        return wall;
    }

    /**
     * Sets the wall.
     *
     * @param wall the new wall
     */
    public void setWall(String wall) {
        this.wall = wall;
    }

    /**
     * Gets the floor.
     *
     * @return the floor
     */
    public String getFloor() {
        
        if (this.floor.isEmpty()) {
            return "0";
        }
        
        return floor;
    }

    /**
     * Sets the floor.
     *
     * @param floor the new floor
     */
    public void setFloor(String floor) {
        this.floor = floor;
    }

    /**
     * Gets the landscape.
     *
     * @return the landscape
     */
    public String getLandscape() {
        return landscape;
    }

    /**
     * Sets the landscape.
     *
     * @param landscape the new landscape
     */
    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    /**
     * Checks if is allow pets.
     *
     * @return true, if is allow pets
     */
    public boolean isAllowPets() {
        return allowPets;
    }

    /**
     * Sets the allow pets.
     *
     * @param allowPets the new allow pets
     */
    public void setAllowPets(boolean allowPets) {
        this.allowPets = allowPets;
    }

    /**
     * Checks if is allow pets eat.
     *
     * @return true, if is allow pets eat
     */
    public boolean isAllowPetsEat() {
        return allowPetsEat;
    }

    /**
     * Sets the allow pets eat.
     *
     * @param allowPetsEat the new allow pets eat
     */
    public void setAllowPetsEat(boolean allowPetsEat) {
        this.allowPetsEat = allowPetsEat;
    }

    /**
     * Checks if is allow walkthrough.
     *
     * @return true, if is allow walkthrough
     */
    public boolean isAllowWalkthrough() {
        return allowWalkthrough;
    }

    /**
     * Sets the allow walkthrough.
     *
     * @param allowWalkthrough the new allow walkthrough
     */
    public void setAllowWalkthrough(boolean allowWalkthrough) {
        this.allowWalkthrough = allowWalkthrough;
    }

    /**
     * Checks for hidden wall.
     *
     * @return true, if successful
     */
    public boolean hasHiddenWall() {
        return hideWall;
    }

    /**
     * Sets the hide wall.
     *
     * @param hideWall the new hide wall
     */
    public void setHideWall(boolean hideWall) {
        this.hideWall = hideWall;
    }

    /**
     * Gets the wall thickness.
     *
     * @return the wall thickness
     */
    public int getWallThickness() {
        return wallThickness;
    }


    /**
     * Sets the wall thickness.
     *
     * @param wallThickness the new wall thickness
     */
    public void setWallThickness(int wallThickness) {
        this.wallThickness = wallThickness;
    }

    /**
     * Gets the wall height.
     *
     * @return the wall height
     */
    public Integer getWallHeight() {
        return -1;
    }

    /**
     * Gets the floor thickness.
     *
     * @return the floor thickness
     */
    public int getFloorThickness() {
        return floorThickness;
    }

    /**
     * Sets the floor thickness.
     *
     * @param floorThickness the new floor thickness
     */
    public void setFloorThickness(int floorThickness) {
        this.floorThickness = floorThickness;
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
        return ownerName;
    }

    /**
     * Gets the thumbnail.
     *
     * @return the thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the thumbnail.
     *
     * @param thumbnail the new thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Gets the room type.
     *
     * @return the room type
     */
    public RoomType getRoomType() {
        return roomType;
    }

    /**
     * Sets the owner id.
     *
     * @param ownerId the new owner id
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Sets the owner name.
     *
     * @param ownerName the new owner name
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Gets the bubble mode.
     *
     * @return the bubble mode
     */
    public int getBubbleMode() {
        return chatType;
    }

    /**
     * Sets the bubble mode.
     *
     * @param chatType the new bubble mode
     */
    public void setBubbleMode(int chatType) {
        this.chatType = chatType;
    }

    /**
     * Gets the bubble type.
     *
     * @return the bubble type
     */
    public int getBubbleType() {
        return chatBalloon;
    }

    /**
     * Sets the bubble type.
     *
     * @param chatBalloon the new bubble type
     */
    public void setBubbleType(int chatBalloon) {
        this.chatBalloon = chatBalloon;
    }

    /**
     * Gets the bubble scroll.
     *
     * @return the bubble scroll
     */
    public int getBubbleScroll() {
        return chatSpeed;
    }

    /**
     * Sets the bubble scroll.
     *
     * @param chatSpeed the new bubble scroll
     */
    public void setBubbleScroll(int chatSpeed) {
        this.chatSpeed = chatSpeed;
    }

    /**
     * Gets the chat max distance.
     *
     * @return the chat max distance
     */
    public int getChatMaxDistance() {
        return chatMaxDistance;
    }

    /**
     * Sets the chat max distance.
     *
     * @param chatMaxDistance the new chat max distance
     */
    public void setChatMaxDistance(int chatMaxDistance) {
        this.chatMaxDistance = chatMaxDistance;
    }

    /**
     * Gets the chat flood protection.
     *
     * @return the chat flood protection
     */
    public int getChatFloodProtection() {
        return chatFloodProtection;
    }

    /**
     * Sets the chat flood protection.
     *
     * @param chatFloodProtection the new chat flood protection
     */
    public void setChatFloodProtection(int chatFloodProtection) {
        this.chatFloodProtection = chatFloodProtection;
    }

    /**
     * Gets the who can mute.
     *
     * @return the who can mute
     */
    public int getWhoCanMute() {
        return whoCanMute;
    }

    /**
     * Sets the who can mute.
     *
     * @param whoCanMute the new who can mute
     */
    public void setWhoCanMute(int whoCanMute) {
        
        if (whoCanMute > 2) {
            return;
        }
        
        this.whoCanMute = whoCanMute;
    }

    /**
     * Gets the who can kick.
     *
     * @return the who can kick
     */
    public int getWhoCanKick() {
        return whoCanKick;
    }

    /**
     * Sets the who can kick.
     *
     * @param whoCanKick the new who can kick
     */
    public void setWhoCanKick(int whoCanKick) {
        
        if (whoCanKick > 2) {
            return;
        }
        
        this.whoCanKick = whoCanKick;
    }

    /**
     * Gets the who can ban.
     *
     * @return the who can ban
     */
    public int getWhoCanBan() {
        return whoCanBan;
    }

    /**
     * Sets the who can ban.
     *
     * @param whoCanBan the new who can ban
     */
    public void setWhoCanBan(int whoCanBan) {
        
        if (whoCanBan > 2) {
            return;
        }
        
        this.whoCanBan = whoCanBan;
    }

    /**
     * Gets the tags.
     *
     * @return the tags
     */
    public String[] getTags() {
        return tags;
    }

    /**
     * Sets the tags.
     *
     * @param tags the new tags
     */
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    /**
     * Sets the wall height.
     *
     * @param wallHeight the new wall height
     */
    public void setWallHeight(int wallHeight) {
        // TODO Auto-generated method stub
        
    }
}
