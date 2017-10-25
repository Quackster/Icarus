package org.alexdev.icarus.game.entity;

import org.alexdev.icarus.game.player.Player;

public enum EntityType {

    PLAYER(Player.class),
    PET(Entity.class),
    BOT(Entity.class);
    
    Class<? extends Entity> clazz;
    
    EntityType(Class<? extends Entity> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Entity> getEntityClass() {
        return clazz;
    }
}