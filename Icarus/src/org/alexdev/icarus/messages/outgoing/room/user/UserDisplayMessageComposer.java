package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class UserDisplayMessageComposer extends OutgoingMessageComposer {

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
					response.writeString(entity.getDetails().getUsername());
					response.writeString(entity.getDetails().getMotto());
					response.writeString(entity.getDetails().getFigure());
					response.writeInt(entity.getRoomUser().getVirtualId());
					response.writeInt(entity.getRoomUser().getPosition().getX());
					response.writeInt(entity.getRoomUser().getPosition().getY());
					response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
					response.writeInt(0);
					response.writeInt(1);
					response.writeString("m");
					response.writeInt(-1);
					response.writeInt(-1);
					response.writeInt(0);
					response.writeInt(1337); // achievement points
					response.writeBool(false);
				}

				if (entity.getType() == EntityType.BOT) {
					response.writeInt(entity.getDetails().getId());
					response.writeString(entity.getDetails().getUsername());
					response.writeString(entity.getDetails().getMotto());
					response.writeString(entity.getDetails().getFigure());
					response.writeInt(entity.getRoomUser().getVirtualId());
					response.writeInt(entity.getRoomUser().getPosition().getX());
					response.writeInt(entity.getRoomUser().getPosition().getY());
					response.writeString(Double.toString(entity.getRoomUser().getPosition().getZ()));
					response.writeInt(0);
					response.writeInt(4); // 2 if pet
					
					// TODO: pet shit here
					
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
			}
		}
	}

}
