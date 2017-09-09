package org.alexdev.icarus.game.player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.moderation.Permission;
import com.google.common.collect.Maps;

public class PlayerManager {

    private static Map<Integer, Player> authenticatedPlayersByID;
    private static Map<String, Player> authenticatedPlayersByName;

    private static List<Permission> permissions;

    static {
        authenticatedPlayersByID = Maps.newConcurrentMap();
        authenticatedPlayersByName = Maps.newConcurrentMap();

        permissions = PlayerDao.getPermissions();
    }

    public static void addPlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            authenticatedPlayersByID.put(player.getDetails().getID(), player);
            authenticatedPlayersByName.put(player.getDetails().getName(), player);
        }
    }
    
    public static void removePlayer(Player player) {

        if (player.getDetails().isAuthenticated()) {
            authenticatedPlayersByID.remove(player.getDetails().getID());
            authenticatedPlayersByName.remove(player.getDetails().getName());
        }
    }

    public static Player getByID(int userID) {

        if (authenticatedPlayersByID.containsKey(userID)) {
            return authenticatedPlayersByID.get(userID);
        }
        
        return null;
    }

    public static Player getByName(String name) {

        if (authenticatedPlayersByName.containsKey(name)) {
            return authenticatedPlayersByName.get(name);
        }
        
        return null;
    }

    public static PlayerDetails getPlayerData(int userID) {

        Player player = getByID(userID);

        if (player == null) {
            return PlayerDao.getDetails(userID);
        }

        return player.getDetails();
    }

    public static boolean checkForDuplicates(Player player) {

        for (Player session : authenticatedPlayersByID.values()) {

            if (session.getDetails().getID() == player.getDetails().getID()) {
                if (session.getNetwork().getConnectionID() != player.getNetwork().getConnectionID()) { // user tries to login twice
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
        return authenticatedPlayersByID.values().stream().filter(p -> p != null).collect(Collectors.toList());
    }
}
