package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.user.UserChangeComposer;
import org.alexdev.icarus.messages.outgoing.user.AvatarAspectUpdateComposer;
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
		
		player.send(new AvatarAspectUpdateComposer(figure, gender));
		

		
		if (player.inRoom()) {
			player.send(new UserChangeComposer(player, true));
			player.getRoom().send(new UserChangeComposer(player, false));
		}

	}

}
