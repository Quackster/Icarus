package org.alexdev.icarus.game.entity;

import org.alexdev.icarus.game.player.Player;

public enum EntityType {

	PLAYER(Player.class),
	PET(IEntity.class),
	BOT(IEntity.class);
	
	Class<? extends IEntity> clazz;
	
	EntityType(Class<? extends IEntity> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends IEntity> getClazz() {
		return clazz;
	}
	
}
