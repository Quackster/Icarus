package org.alexdev.icarus.game.messenger;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.game.player.Player;

public class MessengerUser {

    private int userId;
    private PlayerDetails details;

    public MessengerUser(int userId) {
        this.userId = userId;
        this.details = PlayerManager.getPlayerData(this.userId);
    }

    public void serialise(Response response, boolean forceOffline) {

        response.writeInt(this.details.getId());
        response.writeString(this.details.getName());

        if (forceOffline) {
            response.writeInt(false);
            response.writeBool(false);
            response.writeBool(false);
            response.writeString("");
            response.writeInt(0);
            response.writeString("");  
        } else {

            boolean isUserOnline = this.isUserOnline();
            
            response.writeInt(isUserOnline);
            response.writeBool(isUserOnline);
            response.writeBool(this.inRoom());  
            
            if (isUserOnline) {
                response.writeString(this.details.getFigure());
                response.writeInt(0);
                response.writeString(this.details.getMission());
            } else {
                response.writeString("");
                response.writeInt(0);
                response.writeString("");                
            }
        }

        response.writeString("");
        response.writeString("");
        response.writeBool(true);
        response.writeBool(false);
        response.writeBool(false);
        response.writeShort(0);
    }

    public boolean isUserOnline() {
        return PlayerManager.hasPlayer(this.userId);
    }
    
    public boolean inRoom() {

        if (this.isUserOnline()) {
            Player player = PlayerManager.getById(this.userId);
            return player.inRoom();
        }

        return false;
    }

    public void dispose() {
        this.details = null;
    }

    public Player getPlayer() {
        return PlayerManager.getById(this.userId);
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public int getUserId() {
        return userId;
    }
}
