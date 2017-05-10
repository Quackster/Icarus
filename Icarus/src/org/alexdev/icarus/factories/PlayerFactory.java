package org.alexdev.icarus.factories;

import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.game.player.PlayerDetails;

public class PlayerFactory {

	public static PlayerDetails newDetails() {
		PlayerDetails details = new PlayerDetails(null);
		return details;
	}
	
	public static PlayerDetails getDetails(IEntity entity) {
		PlayerDetails details = new PlayerDetails(entity);
		return details;
	}
	
	public static PlayerDetails getDetails(IEntity entity, int id, String username, String motto, String figure, int rank, int credits) {
		PlayerDetails details = getDetails(entity);
		details.fill(id, username, motto, figure, rank, credits);
		return details;
	}
}
