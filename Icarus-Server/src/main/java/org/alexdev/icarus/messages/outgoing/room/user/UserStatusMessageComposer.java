package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.room.EntityState;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UserStatusMessageComposer extends MessageComposer {

    private List<EntityState> states;

    public UserStatusMessageComposer(ConcurrentLinkedQueue<Entity> entities) {
        createEntityStates(new ArrayList<>(entities));
    }

    public UserStatusMessageComposer(List<Entity> users) {
        createEntityStates(users);
    }

    private void createEntityStates(List<Entity> entities) {

        this.states = new ArrayList<>();

        for (Entity user : entities) {
            this.states.add(new EntityState(
                    user.getRoomUser().getVirtualId(),
                    user.getRoomUser().getPosition(),
                    user.getRoomUser().getStatuses()));
        }
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UserStatusMessageComposer);
        response.writeInt(this.states.size());

        for (EntityState entity : this.states) {
            response.writeObject(entity);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.UserStatusMessageComposer;
    }
}