package org.alexdev.icarus.server.netty.connections;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.server.netty.NettyPlayerNetwork;
import org.jboss.netty.channel.Channel;

public class SessionManager {

    private ConcurrentMap<Integer, Player> sessions;

    public SessionManager() {
        sessions = new ConcurrentHashMap<Integer, Player>();
    }

    /**
     * Adds the session.
     *
     * @param channel the channel
     * @return true, if successful
     */
    public boolean addSession(Channel channel) {

        Player player = new Player(new NettyPlayerNetwork(channel, channel.getId()));
        channel.setAttachment(player);

        return sessions.putIfAbsent(channel.getId(), player) == null;
    }

    /**
     * Removes the session.
     *
     * @param channel the channel
     */
    public void removeSession(Channel channel) { 

        if (sessions.containsKey(channel.getId())) {
            sessions.remove(channel.getId());
        }
    }

    /**
     * Checks for session.
     *
     * @param channel the channel
     * @return true, if successful
     */
    public boolean hasSession(Channel channel) {
        return sessions.containsKey(channel.getId());
    }

    /**
     * Gets the sessions.
     *
     * @return the sessions
     */
    public ConcurrentMap<Integer, Player> getSessions() {
        return sessions;
    }
}
