package org.alexdev.icarus.game.entity;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.RoomUser;

public interface IEntity {

	public PlayerDetails getDetails();
	public RoomUser getRoomUser();
	public EntityType getType();
}
