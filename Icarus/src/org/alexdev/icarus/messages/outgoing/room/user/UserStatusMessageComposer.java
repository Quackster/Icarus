package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;
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
 
        response.init(Outgoing.UserStatusMessageComposer);
        
        synchronized (this.users) {

            response.writeInt(this.users.size());

            for (Entity entity : this.users) {

                response.writeInt(entity.getRoomUser().getVirtualId());
                
                if (entity.getRoomUser().isWalking()) {
                    
                    if (entity.getRoomUser().getNext() == null) {
                        entity.getRoomUser().stopWalking();
                    }
                }
                
                response.writeInt(entity.getRoomUser().getPosition().getX());
                response.writeInt(entity.getRoomUser().getPosition().getY());
                response.writeString(Util.getDecimalFormatter().format(entity.getRoomUser().getPosition().getZ()));
                
                if (entity.getRoomUser().isWalking()) {

                    if (entity.getRoomUser().getNext() != null) {

                        Position next = entity.getRoomUser().getNext();

                        entity.getRoomUser().getPosition().setZ(entity.getRoomUser().getRoom().getMapping().getTile(next.getX(), next.getY()).getHeight());
                        entity.getRoomUser().getPosition().setX(next.getX());
                        entity.getRoomUser().getPosition().setY(next.getY());
                    }
                }
                
                response.writeInt(entity.getRoomUser().getPosition().getHeadRotation());
                response.writeInt(entity.getRoomUser().getPosition().getBodyRotation());

                String statusString = "/";

                for (Entry<EntityStatus, String> status : entity.getRoomUser().getStatuses().entrySet()) {

                    statusString += status.getKey().getStatusCode();

                    if (!status.getValue().isEmpty()) {
                        statusString += " ";
                        statusString += status.getValue();
                    }

                    statusString += "/";
                }

                statusString += " ";
                response.writeString(statusString);
                
                if (entity.getRoomUser().needsUpdate()) {
                    entity.getRoomUser().setNeedUpdate(false);
                }
            }
        }
    }
}
