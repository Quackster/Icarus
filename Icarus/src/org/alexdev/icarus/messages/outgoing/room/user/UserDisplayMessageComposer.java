package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class UserDisplayMessageComposer implements OutgoingMessageComposer {

	private List<IEntity> entities;

	public UserDisplayMessageComposer(IEntity entity) {
		this(Arrays.asList(new IEntity[] { entity }));
	}

	public UserDisplayMessageComposer(List<IEntity> entities) {
		this.entities = entities;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.UserDisplayMessageComposer);
		synchronized (this.entities) {

			response.appendInt32(this.entities.size());
			for (IEntity entity : this.entities) {

				if (entity.getType() == EntityType.PLAYER) {


					response.appendInt32(entity.getDetails().getId());
					response.appendString(entity.getDetails().getUsername());
					response.appendString(entity.getDetails().getMotto());
					response.appendString(entity.getDetails().getFigure());
					response.appendInt32(entity.getRoomUser().getVirtualId());
					response.appendInt32(entity.getRoomUser().getPosition().getX());
					response.appendInt32(entity.getRoomUser().getPosition().getY());
					response.appendString(Double.toString(entity.getRoomUser().getPosition().getZ()));
					response.appendInt32(0);
					response.appendInt32(1);
					response.appendString("m");
					response.appendInt32(-1);
					response.appendInt32(-1);
					response.appendInt32(0);
					response.appendInt32(1337); // achievement points
					response.appendBoolean(false);

				}

				if (entity.getType() == EntityType.BOT) {


					response.appendInt32(entity.getDetails().getId());
					response.appendString(entity.getDetails().getUsername());
					response.appendString(entity.getDetails().getMotto());
					response.appendString(entity.getDetails().getFigure());
					response.appendInt32(entity.getRoomUser().getVirtualId());
					response.appendInt32(entity.getRoomUser().getPosition().getX());
					response.appendInt32(entity.getRoomUser().getPosition().getY());
					response.appendString(Double.toString(entity.getRoomUser().getPosition().getZ()));
					response.appendInt32(0);
					response.appendInt32(4); // 2 if pet
					
					// TODO: pet shit here
					
					response.appendString("m");
		            response.appendInt32(1);
		            response.appendString("Alex");
		            response.appendInt32(5);
		            response.appendShort(1);
		            response.appendShort(2);
		            response.appendShort(3);
		            response.appendShort(4);
		            response.appendShort(5);

				}
			}
		}
	}

}
