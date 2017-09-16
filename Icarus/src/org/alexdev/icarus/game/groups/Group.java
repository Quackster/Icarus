package org.alexdev.icarus.game.groups;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.game.groups.access.GroupAccessType;

public class Group {

    private int id;
    private String title;
    private String description;
    private String badge;
    private int ownerId;
    private int roomId;
    private int colourA;
    private int colourB;
    
    private long created;
    private boolean canMembersDecorate;
    
    private GroupAccessType accessType;
    //private GroupMemberManager memberManager;

    public Group(int id, String title, String description, String badge, int ownerId, int roomId, long created, int colourA, int colourB, boolean canMembersDecorate, GroupAccessType type) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.badge = badge;
        this.ownerId = ownerId;
        this.roomId = roomId;
        this.created = created;
        this.colourA = colourA;
        this.colourB = colourB;
        this.canMembersDecorate = canMembersDecorate;
        this.accessType = type;
    }

    /**
     * Delete the group.
     */
    public void delete() {
        GroupDao.deleteGroup(this.id);
    }
    
    /**
     * Save the group.
     */
    public void save() {
        GroupDao.saveGroup(this);
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
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Gets the badge.
     *
     * @return the badge
     */
    public String getBadge() {
        return badge;
    }

    /**
     * Sets the badge.
     *
     * @param badge the new badge
     */
    public void setBadge(String badge) {
        this.badge = badge;
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
     * Sets the owner id.
     *
     * @param ownerId the new owner id
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Gets the room id.
     *
     * @return the room id
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Sets the room id.
     *
     * @param roomId the new room id
     */
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the created.
     *
     * @return the created
     */
    public long getCreated() {
        return created;
    }

    /**
     * Gets the colour A.
     *
     * @return the colour A
     */
    public int getColourA() {
        return colourA;
    }

    /**
     * Sets the colour A.
     *
     * @param colourA the new colour A
     */
    public void setColourA(int colourA) {
        this.colourA = colourA;
    }

    /**
     * Gets the colour B.
     *
     * @return the colour B
     */
    public int getColourB() {
        return colourB;
    }

    /**
     * Sets the colour B.
     *
     * @param colourB the new colour B
     */
    public void setColourB(int colourB) {
        this.colourB = colourB;
    }

    /**
     * Gets the access type.
     *
     * @return the access type
     */
    public GroupAccessType getAccessType() {
        return accessType;
    }

    /**
     * Sets the access type.
     *
     * @param accessType the new access type
     */
    public void setAccessType(GroupAccessType accessType) {
        this.accessType = accessType;
    }

    /**
     * Can members decorate.
     *
     * @return true, if successful
     */
    public boolean canMembersDecorate() {
        return canMembersDecorate;
    }

    /**
     * Sets the can members decorate.
     *
     * @param canMembersDecorate the new can members decorate
     */
    public void setCanMembersDecorate(boolean canMembersDecorate) {
        this.canMembersDecorate = canMembersDecorate;
    }

}