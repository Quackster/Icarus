package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.List;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class UserDisplayMessageComposer extends MessageComposer {

    private List<Entity> entities;
    
    public UserDisplayMessageComposer(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.UserDisplayMessageComposer);
        
        synchronized (this.entities) {

            this.response.writeInt(this.entities.size());
            
            for (Entity entity : this.entities) {
                if (entity.getType() == EntityType.PLAYER) {
                    this.response.writeInt(entity.getDetails().getId());
                    this.response.writeString(entity.getDetails().getName());
                    this.response.writeString(entity.getDetails().getMission());
                    this.response.writeString(entity.getDetails().getFigure());
                    this.response.writeInt(entity.getRoomUser().getVirtualId());
                    this.response.writeInt(entity.getRoomUser().getPosition().getX());
                    this.response.writeInt(entity.getRoomUser().getPosition().getY());
                    this.response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
                    this.response.writeInt(0);
                    this.response.writeInt(1);
                    this.response.writeString(entity.getDetails().getGender().toLowerCase());
                    this.response.writeInt(-1);
                    this.response.writeInt(-1);
                    this.response.writeInt(0);
                    this.response.writeInt(1337); // achievement points
                    this.response.writeBool(false);
                }

                if (entity.getType() == EntityType.BOT) {
                    this.response.writeInt(entity.getDetails().getId());
                    this.response.writeString(entity.getDetails().getName());
                    this.response.writeString(entity.getDetails().getMission());
                    this.response.writeString(entity.getDetails().getFigure());
                    this.response.writeInt(entity.getRoomUser().getVirtualId());
                    this.response.writeInt(entity.getRoomUser().getPosition().getX());
                    this.response.writeInt(entity.getRoomUser().getPosition().getY());
                    this.response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
                    this.response.writeInt(0);
                    this.response.writeInt(4);
                    this.response.writeString("m");
                    this.response.writeInt(1);
                    this.response.writeString("Alex");
                    this.response.writeInt(5);
                    this.response.writeShort(1);
                    this.response.writeShort(2);
                    this.response.writeShort(3);
                    this.response.writeShort(4);
                    this.response.writeShort(5);
                }
                
                if (entity.getType() == EntityType.PET) {
                    
                    Pet pet = (Pet)entity;
                    
                    this.response.writeInt(entity.getDetails().getId());
                    this.response.writeString(pet.getName());
                    this.response.writeString("");
                    
                    String look = pet.getLook().toLowerCase() + " ";;
                    
                    if (pet.getTypeId() == 15) {
                        look += new StringBuilder().append(pet.isSaddled() ? "3" : "2").append(" 2 ").append(pet.getHair()).append(" ").append(pet.getHairDye()).append(" 3 ").append(pet.getHair()).append(" ").append(pet.getHairDye()).append(pet.isSaddled() ? "0 4 9 0" : "").toString();
                    } else {
                        look += "2 2 -1 0 3 -1 0";
                    }
                    
                    this.response.writeString(look);
                    this.response.writeInt(entity.getRoomUser().getVirtualId());
                    this.response.writeInt(entity.getRoomUser().getPosition().getX());
                    this.response.writeInt(entity.getRoomUser().getPosition().getY());
                    this.response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
                    this.response.writeInt(0);
                    this.response.writeInt(2);
                    this.response.writeInt(pet.getTypeId());
                    this.response.writeInt(pet.getOwnerId());
                    this.response.writeString(pet.getOwnerName());
                    this.response.writeInt(1);
                    this.response.writeBool(pet.isSaddled());
                    this.response.writeBool(pet.isRidingHorse());
                    this.response.writeInt(0);
                    this.response.writeInt(0);
                    this.response.writeString("");
                }
            }
        }
    }
}
