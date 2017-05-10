package org.alexdev.icarus.game.room.bot;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.entity.RoomEntity;

public class Bot implements IEntity {

	private PlayerDetails details;
	private BotRoomUser roomUser;

	public Bot() {
		this.roomUser = new BotRoomUser(this);
		this.details = new PlayerDetails(this);
		this.details.fill(2200, "Lolz", "hello theur", "fa-1201-0.lg-270-91.hd-180-1.sh-300-91.ch-805-84.hr-125-42", 1, 0);
	}
	
	@Override
	public RoomEntity getRoomUser() {
		return this.roomUser;
	}

	@Override
	public EntityType getType() {
		return EntityType.BOT;
	}

	@Override
	public PlayerDetails getDetails() {
		return details;
	}

}
