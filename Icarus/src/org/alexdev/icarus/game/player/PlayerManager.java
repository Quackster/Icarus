package org.alexdev.icarus.game.player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.game.moderation.Permission;

import com.google.common.collect.Maps;

public class PlayerManager {

    private static Map<Integer, Player> players;
    private static List<Permission> permissions;

    static {
        players = Maps.newConcurrentMap();
        permissions = PlayerDao.getPermissions();
    }

    public static Player getById(int userId) {
        
        try {
            return players.values().stream().filter(s -> s.getDetails().getId() == userId).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Player getByName(String name) {
        
        try {
            return players.values().stream().filter(s -> s.getDetails().getName().equals(name)).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }
    
    public static PlayerDetails getPlayerData(int userId) {
        
        Player player = getById(userId);
        
        if (player == null) {
            return PlayerDao.getDetails(userId);
        }
        
        return player.getDetails();
    }
    
    public static boolean checkForDuplicates(Player player) {
        
        for (Player session : players.values()) {
            
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
        return players.values().stream().filter(p -> p != null).collect(Collectors.toList());
    }
    
    public static Map<Integer, Player> getConnectedPlayers() {
        return players;
    }
}
