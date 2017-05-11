package org.alexdev.icarus.messages.incoming.room.user;

import java.util.LinkedList;

import org.alexdev.icarus.game.pathfinder.Pathfinder;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.game.room.model.Position;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class UserWalkMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage request) {

		int X = request.readInt();
		int Y = request.readInt();
		
		if (player.getRoomUser().getRoom().getData().getModel().isBlocked(X, Y)) {
			return;
		}
		
		if (player.getRoomUser().getPosition().sameAs(new Position(X, Y))) {
			return;
		}

		RoomUser roomUser = player.getRoomUser();
		roomUser.getGoal().setX(X);
		roomUser.getGoal().setY(Y);

		LinkedList<Position> path = Pathfinder.makePath(player);

		if (path == null) {
			return;
		}

		if (path.size() == 0) {
			return;
		}
		
		roomUser.setPath(path);
		roomUser.setWalking(true);
	}
}
