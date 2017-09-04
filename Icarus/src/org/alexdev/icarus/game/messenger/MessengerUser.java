package org.alexdev.icarus.game.messenger;

import org.alexdev.icarus.dao.mysql.PlayerDao;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.game.player.Player;

public class MessengerUser {

    private int userId;
    private PlayerDetails details;
    private Player player;
    
    public MessengerUser(int userId) {
        this.userId = userId;

        if (this.isOnline()) {
            this.details = this.player.getDetails();
        } else {
            this.details = PlayerDao.getDetails(this.userId);
        }
    }

    public void update() {
        this.player = PlayerManager.getById(this.userId);
    }

    public void serialiseFriend(Response response, boolean forceOffline) {
        response.writeInt(this.getDetails().getId());
        response.writeString(this.getDetails().getName());
        response.writeInt(forceOffline ? false : this.isOnline());
        response.writeBool(forceOffline ? false : this.isOnline());
        response.writeBool(forceOffline ? false : this.inRoom());

        if (forceOffline) {
            response.writeString("");
            response.writeInt(0);
            response.writeString("");  
        } else {
            response.writeString(this.isOnline() ? this.getDetails().getFigure() : "");
            response.writeInt(0);
            response.writeString(this.isOnline() ? this.getDetails().getMission() : "");  
        }

        response.writeString("");
        response.writeString("");
        response.writeBool(true);
        response.writeBool(false);
        response.writeBool(false);
        response.writeShort(0); 
    }

    public void serialiseSearch(Response response) {
        response.writeInt(this.getDetails().getId());
        response.writeString(this.getDetails().getName());
        response.writeString(this.getDetails().getMission()); 
        response.writeBool(this.isOnline());
        response.writeBool(this.inRoom());
        response.writeString("");
        response.writeInt(0);
        response.writeString(this.isOnline() ? this.getDetails().getFigure() : ""); 
        response.writeString("");
    }

    public void dispose() {
        this.player = null;
        this.details = null;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isOnline() {
        this.update();
        return player != null;
    }

    public boolean inRoom() {
        return isOnline() ? player.inRoom() : false;
    }

}
