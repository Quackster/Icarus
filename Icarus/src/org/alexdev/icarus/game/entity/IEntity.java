package org.alexdev.icarus.game.entity;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.entity.RoomEntity;

public interface IEntity {

	public PlayerDetails getDetails();
	public RoomEntity getRoomUser();
	public EntityType getType();
}
