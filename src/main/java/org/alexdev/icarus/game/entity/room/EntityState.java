package org.alexdev.icarus.game.entity.room;

import java.util.HashMap;
import java.util.Map.Entry;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Serialisable;

public class EntityState implements Serialisable {

    private int virtualId;
    private Position position;
    private String statusString;
     
    public EntityState(int virtualId, Position position, HashMap<EntityStatus, String> statuses) {
        this.virtualId = virtualId;
        this.position = position.copy();
        this.statusString = "/";

        for (Entry<EntityStatus, String> status :  statuses.entrySet()) {
            this.statusString += status.getKey().getStatusCode();

            if (status.getValue().length() > 0) {
                this.statusString += " ";
                this.statusString += status.getValue();
            }

            this.statusString += "/";
        }

        this.statusString += "/";
    }
    
    @Override
    public void compose(Response msg) {
        msg.writeInt(this.virtualId);
        msg.writeInt(this.position.getX());
        msg.writeInt(this.position.getY());
        msg.writeString(String.valueOf(this.position.getZ()));
        msg.writeInt(this.position.getHeadRotation());
        msg.writeInt(this.position.getBodyRotation());
        msg.writeString(this.statusString);
    }
}
