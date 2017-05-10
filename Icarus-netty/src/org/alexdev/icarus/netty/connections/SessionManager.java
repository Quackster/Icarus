package org.alexdev.icarus.netty.connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.netty.NettyPlayerNetwork;
import org.jboss.netty.channel.Channel;

public class SessionManager {
	
	private ConcurrentMap<Integer, Player> sessions;

	public SessionManager() {
		sessions = new ConcurrentHashMap<Integer, Player>();
	}
	
	public boolean addSession(Channel channel) {
		
		Player player = new Player(new NettyPlayerNetwork(channel, channel.getId()));
		channel.setAttachment(player);
		
		Icarus.getGame().getPlayerManager().getPlayers().put(channel.getId(), player);
		return sessions.putIfAbsent(channel.getId(), player) == null;
	}

	public void removeSession(Channel channel) { 
		try {
			Icarus.getGame().getPlayerManager().getPlayers().remove(channel.getId());
			sessions.remove(channel.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasSession(Channel channel) {
		return sessions.containsKey(channel.getId());
	}

	public ConcurrentMap<Integer, Player> getSessions() {
		return sessions;
	}
}
