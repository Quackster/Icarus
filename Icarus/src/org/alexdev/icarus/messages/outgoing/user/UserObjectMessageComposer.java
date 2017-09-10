package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class UserObjectMessageComposer extends MessageComposer {

    private PlayerDetails details;

    public UserObjectMessageComposer(PlayerDetails details) {
        this.details = details;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.UserObjectMessageComposer);
        this.response.writeInt(this.details.getId()); // User Id
        this.response.writeString(this.details.getName()); // Username
        this.response.writeString(this.details.getFigure()); // Figure
        this.response.writeString(this.details.getGender().toUpperCase()); // Gender
        this.response.writeString(this.details.getMission()); // Motto
        this.response.writeString(""); // ?
        this.response.writeBool(false); // ?
        this.response.writeInt(0); // Respect
        this.response.writeInt(3); // Daily Respect Points
        this.response.writeInt(3); // Daily Pet Respect Points
        this.response.writeBool(true); // ?
        this.response.writeString("1448526834"); // Last Online (format?)
        this.response.writeBool(false); // Can Change Username
        this.response.writeBool(false); // ?
    }

}
