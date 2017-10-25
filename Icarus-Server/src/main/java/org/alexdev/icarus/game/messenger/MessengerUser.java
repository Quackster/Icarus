package org.alexdev.icarus.game.messenger;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.game.player.Player;

public class MessengerUser {

    private int userId;
    private PlayerDetails details;

    public MessengerUser(int userId) {
        this.userId = userId;
        this.details = PlayerManager.getInstance().getPlayerData(this.userId);

        if (this.details == null) {
            Log.getErrorLogger().error("Could not find friend with user id {}", this.userId);
        }
    }

    /**
     * Serialise the friend data.
     *
     * @param msg the response
     * @param forceOffline the force offline
     */
    public void serialise(Response msg, boolean forceOffline) {
        msg.writeInt(this.getDetails().getId());
        msg.writeString(this.getDetails().getName());
        msg.writeInt(forceOffline ? false : this.isUserOnline());
        msg.writeBool(forceOffline ? false : this.isUserOnline());
        msg.writeBool(forceOffline ? false : this.inRoom());

        if (forceOffline) {
            msg.writeString("");
            msg.writeInt(0);
            msg.writeString("");  
        } else {
            msg.writeString(this.isUserOnline() ? this.getDetails().getFigure() : "");
            msg.writeInt(0);
            msg.writeString(this.isUserOnline() ? this.getDetails().getMission() : "");  
        }

        msg.writeString("");
        msg.writeString("");
        msg.writeBool(true);
        msg.writeBool(false);
        msg.writeBool(false);
        msg.writeShort(0); 
    }

    /**
     * Checks if is user online.
     *
     * @return true, if is user online
     */
    public boolean isUserOnline() {
        return PlayerManager.getInstance().hasPlayer(this.userId);
    }
    
    /**
     * In room.
     *
     * @return true, if successful
     */
    public boolean inRoom() {

        if (this.isUserOnline()) {
            Player player = PlayerManager.getInstance().getById(this.userId);
            return player.inRoom();
        }

        return false;
    }

    /**
     * Dispose.
     */
    public void dispose() {
        this.details = null;
    }

    /**
     * Gets the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return PlayerManager.getInstance().getById(this.userId);
    }

    /**
     * Gets the details.
     *
     * @return the details
     */
    public PlayerDetails getDetails() {
        return details;
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }
}
