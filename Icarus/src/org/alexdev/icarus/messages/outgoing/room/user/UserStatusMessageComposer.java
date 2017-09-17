package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.util.Util;

public class UserStatusMessageComposer extends MessageComposer {

    private List<Entity> users;

    public UserStatusMessageComposer(Entity entity) {
        this(Arrays.asList(new Entity[] { entity }));
    }


    public UserStatusMessageComposer(List<Entity> users) {
        this.users = users;
    }

    @Override
    public void write() {

        this.response.init(Outgoing.UserStatusMessageComposer);

        synchronized (this.users) {

            this.response.writeInt(this.users.size());

            for (Entity entity : this.users) {

                RoomUser roomUser = entity.getRoomUser();
                this.response.writeInt(roomUser.getVirtualId());

                if (roomUser.isWalking()) {
                    if (roomUser.getPositionToSet() == null) {
                        roomUser.stopWalking();
                    }
                }

                this.response.writeInt(roomUser.getPosition().getX());
                this.response.writeInt(roomUser.getPosition().getY());
                this.response.writeString(Util.getDecimalFormatter().format(roomUser.getPosition().getZ()));

                if (roomUser.isWalking()) {
                    if (roomUser.getPositionToSet() != null) {
                        roomUser.getPosition().setX(roomUser.getPositionToSet().getX());
                        roomUser.getPosition().setY(roomUser.getPositionToSet().getY());
                        roomUser.updateNewHeight(roomUser.getPositionToSet());
                    }
                }
                this.response.writeInt(roomUser.getPosition().getHeadRotation());
                this.response.writeInt(roomUser.getPosition().getBodyRotation());

                String statusString = "/";

                for (Entry<EntityStatus, String> status : roomUser.getStatuses().entrySet()) {

                    statusString += status.getKey().getStatusCode();

                    if (!status.getValue().isEmpty()) {
                        statusString += " ";
                        statusString += status.getValue();
                    }

                    statusString += "/";
                }

                statusString += " ";

                this.response.writeString(statusString);
            }
        }
    }
}
