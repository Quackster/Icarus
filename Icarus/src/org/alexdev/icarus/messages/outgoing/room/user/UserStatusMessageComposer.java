package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UserStatusMessageComposer implements OutgoingMessageComposer {

	private List<Entity> users;

	public UserStatusMessageComposer(Entity entity) {
		this(Arrays.asList(new Entity[] { entity }));
	}


	public UserStatusMessageComposer(List<Entity> users) {
		this.users = users;
	}

	@Override
	public void write(Response response) {
		
		response.init(Outgoing.UserStatusMessageComposer);
		
		synchronized (this.users) {

			response.writeInt(this.users.size());

			for (Entity  user : this.users) {

				response.writeInt(user.getRoomUser().getVirtualId());
				response.writeInt(user.getRoomUser().getPosition().getX());
				response.writeInt(user.getRoomUser().getPosition().getY());
				response.writeString(Double.toString(user.getRoomUser().getPosition().getZ()));
				response.writeInt(user.getRoomUser().getHeadRotation());
				response.writeInt(user.getRoomUser().getRotation());

				String status = "/";

				for (Entry<String, String> set : user.getRoomUser().getStatuses().entrySet()) {
					status += set.getKey() + set.getValue() + "/";
				}

				response.writeString(status + "/");
			}
		}
	}

}
