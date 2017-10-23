package org.alexdev.icarus.game.player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.dao.mysql.site.SiteDao;
import org.alexdev.icarus.game.moderation.Permission;

public class PlayerManager {

    private static List<Permission> permissions;
    private static Map<Integer, Player> authenticatedPlayersById;
    private static Map<String, Player> authenticatedPlayersByName;

    static {
        authenticatedPlayersById = new ConcurrentHashMap<>();
        authenticatedPlayersByName = new ConcurrentHashMap<>();
        permissions = PlayerDao.getPermissions();
    }

    /**
     * Adds the player.
     *
     * @param player the player
     */
    public static void addPlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            authenticatedPlayersById.put(player.getEntityId(), player);
            authenticatedPlayersByName.put(player.getDetails().getName(), player);
            SiteDao.updateKey("users.online", authenticatedPlayersById.size());
        }
    }

    /**
     * Removes the player.
     *
     * @param player the player
     */
    public static void removePlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            authenticatedPlayersById.remove(player.getEntityId());
            authenticatedPlayersByName.remove(player.getDetails().getName());
            SiteDao.updateKey("users.online", authenticatedPlayersById.size());
        }
    }

    /**
     * Gets the {@link Player} by id.
     *
     * @param userId the user id
     * @return {@link Player} the by id
     */
    public static Player getById(int userId) {

        if (authenticatedPlayersById.containsKey(userId)) {
            return authenticatedPlayersById.get(userId);
        }

        return null;
    }

    /**
     * Gets the {@link Player} by name.
     *
     * @param name the name
     * @return {@link Player} the by name
     */
    public static Player getByName(String name) {

        if (authenticatedPlayersByName.containsKey(name)) {
            return authenticatedPlayersByName.get(name);
        }

        return null;
    }

    /**
     * Checks for player.
     *
     * @param userId the user id
     * @return true, if successful
     */
    public static boolean hasPlayer(int userId) {
        return authenticatedPlayersById.containsKey(userId);
    }

    /**
     * Checks for player.
     *
     * @param name the name
     * @return true, if successful
     */
    public static boolean hasPlayer(String name) {
        return authenticatedPlayersByName.containsKey(name);
    }


    /**
     * Gets the player data, will try and retrieve logged in player data
     * before querying the database for it.
     *
     * @param userId the user id
     * @return the player data
     */
    public static PlayerDetails getPlayerData(int userId) {

        Player player = getById(userId);

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
    public static void sendMessage(String message) {
        
        for (Player player : authenticatedPlayersById.values()) {
            player.sendMessage(message);
        }
    }

    /**
     * Check for duplicates.
     *
     * @param player the player
     * @return true, if successful
     */
    public static boolean kickDuplicates(Player player) {

        for (Player session : authenticatedPlayersById.values()) {

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
    public static boolean hasPermission(int rank, String perm) {

        for (Permission permission  : permissions) {

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
    public static List<Player> getPlayers() {
        return authenticatedPlayersById.values().stream().filter(p -> p != null).collect(Collectors.toList());
    }
}
