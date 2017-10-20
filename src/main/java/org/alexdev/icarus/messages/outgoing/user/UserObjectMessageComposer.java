package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UserObjectMessageComposer extends MessageComposer {

    private PlayerDetails details;

    public UserObjectMessageComposer(PlayerDetails details) {
        this.details = details;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UserObjectMessageComposer);
        response.writeInt(this.details.getId()); // User Id
        response.writeString(this.details.getName()); // Username
        response.writeString(this.details.getFigure()); // Figure
        response.writeString(this.details.getGender().toUpperCase()); // Gender
        response.writeString(this.details.getMission()); // Motto
        response.writeString(""); // ?
        response.writeBool(false); // ?
        response.writeInt(0); // Respect
        response.writeInt(3); // Daily Respect Points
        response.writeInt(3); // Daily Pet Respect Points
        response.writeBool(true); // ?
        response.writeString("1448526834"); // Last Online (format?)
        response.writeBool(false); // Can Change Username
        response.writeBool(false); // ?
    }

    @Override
    public short getHeader() {
        return Outgoing.UserObjectMessageComposer;
    }
}
