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
    private long created;
    private int colourA;
    private int colourB;
    private boolean canMembersDecorate;
    private GroupAccessType accessType;

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

    public void delete() {
        GroupDao.deleteGroup(this.id);
    }
    
    public void save() {
        GroupDao.saveGroup(this);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public long getCreated() {
        return created;
    }

    public int getColourA() {
        return colourA;
    }

    public void setColourA(int colourA) {
        this.colourA = colourA;
    }

    public int getColourB() {
        return colourB;
    }

    public void setColourB(int colourB) {
        this.colourB = colourB;
    }

    public GroupAccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(GroupAccessType accessType) {
        this.accessType = accessType;
    }

    public boolean canMembersDecorate() {
        return canMembersDecorate;
    }

    public void setCanMembersDecorate(boolean canMembersDecorate) {
        this.canMembersDecorate = canMembersDecorate;
    }

}