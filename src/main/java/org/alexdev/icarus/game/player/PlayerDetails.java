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

    /**
     * Fills the player data.
     *
     * @param id the id
     * @param username the username
     * @param motto the motto
     * @param figure the figure
     * @param gender the gender
     * @param rank the rank
     * @param credits the credits
     * @param homeRoomId the home room id
     */
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
    
    /**
     * Checks for permission.
     *
     * @param permission the permission
     * @return true, if successful
     */
    public boolean hasPermission(String permission) {
        return PlayerManager.hasPermission(this.rank, permission);
    }

    /**
     * Save the details
     */
    public void save() {
        if (this.entity.getType() == EntityType.PLAYER) {
            PlayerDao.save(this);
        }
    }

    /**
     * Send credits.
     */
    public void sendCredits() {
        if (this.entity.getType() == EntityType.PLAYER) {
            ((Player)this.entity).send(new CreditsMessageComposer(this.credits));
        }
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
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Checks if is authenticated.
     *
     * @return true, if is authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Sets the authenticated.
     *
     * @param authenticated the new authenticated
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
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
     * @param username the new name
     */
    public void setName(String username) {
        this.name = username;
    }

    /**
     * Gets the mission.
     *
     * @return the mission
     */
    public String getMission() {
        return motto;
    }

    /**
     * Sets the motto.
     *
     * @param motto the new motto
     */
    public void setMotto(String motto) {
        this.motto = motto;
    }

    /**
     * Gets the figure.
     *
     * @return the figure
     */
    public String getFigure() {
        return figure;
    }

    /**
     * Sets the figure.
     *
     * @param figure the new figure
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets the rank.
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Sets the credits.
     *
     * @param newTotal the new credits
     */
    public void setCredits(int newTotal) {
        this.credits = newTotal;
    }

    /**
     * Gets the credits.
     *
     * @return the credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Gets the entity.
     *
     * @return the entity
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Gets the achievement points.
     *
     * @return the achievement points
     */
    public Integer getAchievementPoints() {
        return 0;
    }

    /**
     * Gets the home room id.
     *
     * @return the home room id
     */
    public int getHomeRoomId() {
        return homeRoomId;
    }

    /**
     * Sets the home room id.
     *
     * @param homeRoomId the new home room id
     */
    public void setHomeRoomId(int homeRoomId) {
        this.homeRoomId = homeRoomId;
    }
}
