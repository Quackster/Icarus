package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.room.user.UserChangeMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.AvatarAspectUpdateMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.UserObjectMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ChangeAppearanceMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        String gender = reader.readString();
        String figure = reader.readString(); // Yes I know this shit is prone to scripting, I'll come back to this when I add buyable clothes
        
        if (!gender.equals("M") && !gender.equals("F")) {
            player.getNetwork().close(); // You try to send an invalid gender? Fuck off.
            return;
        }
        
        player.getDetails().setFigure(figure);
        player.getDetails().setGender(gender);
        player.getDetails().save();
        
        player.send(new AvatarAspectUpdateMessageComposer(figure, gender));
        player.send(new UserObjectMessageComposer(player.getDetails()));

        
        if (player.inRoom()) {
            player.send(new UserChangeMessageComposer(player, true));
            player.getRoom().send(new UserChangeMessageComposer(player, false));
        }
    }
}
