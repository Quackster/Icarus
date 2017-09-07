package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class UserDisplayMessageComposer extends MessageComposer {

    private List<Entity> entities;

    public UserDisplayMessageComposer(Entity entity) {
        this(Arrays.asList(new Entity[] { entity }));
    }

    public UserDisplayMessageComposer(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public void write() {
        
        response.init(Outgoing.UserDisplayMessageComposer);
        
        synchronized (this.entities) {

            response.writeInt(this.entities.size());
            
            for (Entity entity : this.entities) {
                if (entity.getType() == EntityType.PLAYER) {
                    response.writeInt(entity.getDetails().getId());
                    response.writeString(entity.getDetails().getName());
                    response.writeString(entity.getDetails().getMission());
                    response.writeString(entity.getDetails().getFigure());
                    response.writeInt(entity.getRoomUser().getVirtualId());
                    response.writeInt(entity.getRoomUser().getPosition().getX());
                    response.writeInt(entity.getRoomUser().getPosition().getY());
                    response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
                    response.writeInt(0);
                    response.writeInt(1);
                    response.writeString(entity.getDetails().getGender().toLowerCase());
                    response.writeInt(-1);
                    response.writeInt(-1);
                    response.writeInt(0);
                    response.writeInt(1337); // achievement points
                    response.writeBool(false);
                }

                if (entity.getType() == EntityType.BOT) {
                    response.writeInt(entity.getDetails().getId());
                    response.writeString(entity.getDetails().getName());
                    response.writeString(entity.getDetails().getMission());
                    response.writeString(entity.getDetails().getFigure());
                    response.writeInt(entity.getRoomUser().getVirtualId());
                    response.writeInt(entity.getRoomUser().getPosition().getX());
                    response.writeInt(entity.getRoomUser().getPosition().getY());
                    response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
                    response.writeInt(0);
                    response.writeInt(4);
                    response.writeString("m");
                    response.writeInt(1);
                    response.writeString("Alex");
                    response.writeInt(5);
                    response.writeShort(1);
                    response.writeShort(2);
                    response.writeShort(3);
                    response.writeShort(4);
                    response.writeShort(5);
                }
                
                if (entity.getType() == EntityType.PET) {
                    
                    Pet pet = (Pet)entity;
                    
                    response.writeInt(entity.getDetails().getId());
                    response.writeString(pet.getName());
                    response.writeString("");
                    
                    String look = pet.getLook().toLowerCase() + " ";;
                    
                    if (pet.getTypeId() == 15) {
                        look += new StringBuilder().append(pet.isSaddled() ? "3" : "2").append(" 2 ").append(pet.getHair()).append(" ").append(pet.getHairDye()).append(" 3 ").append(pet.getHair()).append(" ").append(pet.getHairDye()).append(pet.isSaddled() ? "0 4 9 0" : "").toString();
                    } else {
                        look += "2 2 -1 0 3 -1 0";
                    }
                    
                    response.writeString(look);
                    response.writeInt(entity.getRoomUser().getVirtualId());
                    response.writeInt(entity.getRoomUser().getPosition().getX());
                    response.writeInt(entity.getRoomUser().getPosition().getY());
                    response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
                    response.writeInt(0);
                    response.writeInt(2);
                    response.writeInt(pet.getTypeId());
                    response.writeInt(pet.getOwnerId());
                    response.writeString(pet.getOwnerName());
                    response.writeInt(1);
                    response.writeBool(pet.isSaddled());
                    response.writeBool(pet.isRidingHorse());
                    response.writeInt(0);
                    response.writeInt(0);
                    response.writeString("");
                }
            }
        }
    }
}
