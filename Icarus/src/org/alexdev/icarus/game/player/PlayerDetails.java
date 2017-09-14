package org.alexdev.icarus.game.player;

import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.messages.outgoing.user.CreditsMessageComposer;

public class PlayerDetails {

    private int id = -1;
    private String name = "null";
    private String motto = "";
    private String figure = "ch-210-66.hr-100-0.sh-290-80.hd-180-7.lg-270-82";
    private String gender = "M";
    private int rank;
    private int credits;
    private int homeRoomId;

    private boolean authenticated;
    private Entity entity;

    public PlayerDetails(Entity entity) {
        this.id = 0;
        this.authenticated = false;
        this.entity = entity;
    }

    public void fill(int id, String username, String motto, String figure, String gender, int rank, int credits, int homeRoomId) {
        this.id = id;
        this.name = username;
        this.motto = motto;
        this.figure = figure;
        this.gender = gender;
        this.rank = rank;
        this.credits = credits;
        this.homeRoomId = homeRoomId;
        this.authenticated = true;
    }
    
    public boolean hasPermission(String permission) {
        return PlayerManager.hasPermission(this.rank, permission);
    }

    public void save() {
        if (this.entity.getType() == EntityType.PLAYER) {
            PlayerDao.save(this);
        }
    }

    public void sendCredits() {
        if (this.entity.getType() == EntityType.PLAYER) {
            ((Player)this.entity).send(new CreditsMessageComposer(this.credits));
        }
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getMission() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getRank() {
        return rank;
    }

    public void setCredits(int newTotal) {
        this.credits = newTotal;
    }

    public int getCredits() {
        return credits;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Integer getAchievementPoints() {
        return 0;
    }

    public int getHomeRoomId() {
        return homeRoomId;
    }

    public void setHomeRoomId(int homeRoomId) {
        this.homeRoomId = homeRoomId;
    }
}
