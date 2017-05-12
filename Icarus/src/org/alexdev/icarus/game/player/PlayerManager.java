package org.alexdev.icarus.game.player;

import java.util.concurrent.ConcurrentHashMap;

import org.alexdev.icarus.dao.mysql.PlayerDao;

public class PlayerManager {

	private static ConcurrentHashMap<Integer, Player> players;
	
	static {
		players = new ConcurrentHashMap<Integer, Player>();
	}

	public static Player findById(int userId) {
		
		try {
			return players.values().stream().filter(s -> s.getDetails().getId() == userId).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Player findByName(String name) {
		
		try {
			return players.values().stream().filter(s -> s.getDetails().getUsername().equals(name)).findFirst().get();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static PlayerDetails getPlayerData(int userId) {
		
		Player player = findById(userId);
		
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
	
	
	public static ConcurrentHashMap<Integer, Player> getPlayers() {
		return players;
	}
}
