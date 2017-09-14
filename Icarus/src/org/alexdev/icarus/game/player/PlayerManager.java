package org.alexdev.icarus.game.player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.moderation.Permission;
import com.google.common.collect.Maps;

public class PlayerManager {

    private static Map<Integer, Player> authenticatedPlayersById;
    private static Map<String, Player> authenticatedPlayersByName;

    private static List<Permission> permissions;

    static {
        authenticatedPlayersById = Maps.newConcurrentMap();
        authenticatedPlayersByName = Maps.newConcurrentMap();

        permissions = PlayerDao.getPermissions();
    }

    public static void addPlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            authenticatedPlayersById.put(player.getDetails().getId(), player);
            authenticatedPlayersByName.put(player.getDetails().getName(), player);
        }
    }
    
    public static void removePlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            authenticatedPlayersById.remove(player.getDetails().getId());
            authenticatedPlayersByName.remove(player.getDetails().getName());
        }
    }

    public static Player getById(int userId) {

        if (authenticatedPlayersById.containsKey(userId)) {
            return authenticatedPlayersById.get(userId);
        }
        
        return null;
    }

    public static Player getByName(String name) {

        if (authenticatedPlayersByName.containsKey(name)) {
            return authenticatedPlayersByName.get(name);
        }
        
        return null;
    }
    
    public static boolean hasPlayer(int userId) {
        return authenticatedPlayersById.containsKey(userId);
    }
    
    public static boolean hasPlayer(String name) {
        return authenticatedPlayersByName.containsKey(name);
    }


    public static PlayerDetails getPlayerData(int userId) {

        Player player = getById(userId);

        if (player == null) {
            return PlayerDao.getDetails(userId);
        }

        return player.getDetails();
    }

    public static boolean checkForDuplicates(Player player) {

        for (Player session : authenticatedPlayersById.values()) {

            if (session.getDetails().getId() == player.getDetails().getId()) {
                if (session.getNetwork().getConnectionId() != player.getNetwork().getConnectionId()) { // user tries to login twice
                    return true;
                }
            }
        }
        return false;
    }

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

    public static List<Player> getPlayers() {
        return authenticatedPlayersById.values().stream().filter(p -> p != null).collect(Collectors.toList());
    }
}
