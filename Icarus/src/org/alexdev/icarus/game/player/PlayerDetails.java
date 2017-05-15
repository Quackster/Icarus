package org.alexdev.icarus.game.player;

import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.messages.outgoing.user.CreditsMessageComposer;

public class PlayerDetails {

    private int id = -1;
    private String username = "Alex";
    private String motto = "banana man";
    private String figure;
    private String gender;
    private int rank;
    private int credits;

    private boolean authenticated;
    private Entity entity;

    public PlayerDetails(Entity session) {
        this.authenticated = false;
        this.entity = session;
    }

    public void fill(int id, String username, String motto, String figure, String gender, int rank, int credits) {
        this.id = id;
        this.username = username;
        this.motto = motto;
        this.figure = figure;
        this.gender = gender;
        this.rank = rank;
        this.credits = credits;
        this.authenticated = true;
    }

    public void save() {
        PlayerDao.save(this);
    }
    
    public boolean hasFuse(String fuse) {
        return false;
    }

    public int getId() {
        return id;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void sendCredits() {
        if (this.entity instanceof Player) {
            Player player = (Player)this.entity;
            player.send(new CreditsMessageComposer(this.credits));
        }
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
}
