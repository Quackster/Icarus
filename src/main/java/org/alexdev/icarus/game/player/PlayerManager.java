package org.alexdev.icarus.game.player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.dao.mysql.site.SiteDao;
import org.alexdev.icarus.game.moderation.Permission;
import org.alexdev.icarus.game.room.RoomManager;

public class PlayerManager {

    private List<Permission> permissions;
    private Map<Integer, Player> authenticatedPlayersById;
    private Map<String, Player> authenticatedPlayersByName;

    private static PlayerManager instance;

    public PlayerManager() {
        this.authenticatedPlayersById = new ConcurrentHashMap<>();
        this.authenticatedPlayersByName = new ConcurrentHashMap<>();
        this.permissions = PlayerDao.getPermissions();
    }

    /**
     * Adds the player.
     *
     * @param player the player
     */
    public void addPlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            this.authenticatedPlayersById.put(player.getEntityId(), player);
            this.authenticatedPlayersByName.put(player.getDetails().getName(), player);
            SiteDao.updateKey("users.online", this.authenticatedPlayersById.size());
        }
    }

    /**
     * Removes the player.
     *
     * @param player the player
     */
    public void removePlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            this.authenticatedPlayersById.remove(player.getEntityId());
            this.authenticatedPlayersByName.remove(player.getDetails().getName());
            SiteDao.updateKey("users.online", this.authenticatedPlayersById.size());
        }
    }

    /**
     * Gets the {@link Player} by id.
     *
     * @param userId the user id
     * @return {@link Player} the by id
     */
    public Player getById(int userId) {

        if (this.authenticatedPlayersById.containsKey(userId)) {
            return this.authenticatedPlayersById.get(userId);
        }

        return null;
    }

    /**
     * Gets the {@link Player} by name.
     *
     * @param name the name
     * @return {@link Player} the by name
     */
    public Player getByName(String name) {

        if (this.authenticatedPlayersByName.containsKey(name)) {
            return this.authenticatedPlayersByName.get(name);
        }

        return null;
    }

    /**
     * Checks for player.
     *
     * @param userId the user id
     * @return true, if successful
     */
    public boolean hasPlayer(int userId) {
        return this.authenticatedPlayersById.containsKey(userId);
    }

    /**
     * Checks for player.
     *
     * @param name the name
     * @return true, if successful
     */
    public boolean hasPlayer(String name) {
        return this.authenticatedPlayersByName.containsKey(name);
    }


    /**
     * Gets the player data, will try and retrieve logged in player data
     * before querying the database for it.
     *
     * @param userId the user id
     * @return the player data
     */
    public PlayerDetails getPlayerData(int userId) {

        Player player = this.getById(userId);

        if (player == null) {
            return PlayerDao.getDetails(userId);
        }

        return player.getDetails();
    }
    
    /**
     * Send message to entire server.
     *
     * @param message the message
     */
    public void sendMessage(String message) {
        
        for (Player player : this.authenticatedPlayersById.values()) {
            player.sendMessage(message);
        }
    }

    /**
     * Check for duplicates.
     *
     * @param player the player
     * @return true, if successful
     */
    public boolean kickDuplicates(Player player) {

        for (Player session : this.authenticatedPlayersById.values()) {

            if (session.getDetails().getId() == player.getEntityId()) {
                if (session.getNetwork().getConnectionId() != player.getNetwork().getConnectionId()) { // user tries to login twice
                    session.getNetwork().close();
                    break;
                }
            }
        }
        
        return false;
    }

    /**
     * Checks for permission.
     *
     * @param rank the rank
     * @param perm the permission
     * @return true, if successful
     */
    public boolean hasPermission(int rank, String perm) {

        for (Permission permission : this.permissions) {

            if (permission.getPermission().equals(perm)) {

                if (permission.isInheritable()) {
                    if (rank >= permission.getRank()) {
                        return true;
                    }
                } else {
                    if (rank == permission.getRank()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Gets the players.
     *
     * @return the players
     */
    public List<Player> getPlayers() {
        return this.authenticatedPlayersById.values().stream().filter(p -> p != null).collect(Collectors.toList());
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static PlayerManager getInstance() {

        if (instance == null) {
            instance = new PlayerManager();
        }

        return instance;
    }
}
