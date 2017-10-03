package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.ArrayList;
import java.util.List;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.room.EntityState;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class UserStatusMessageComposer extends MessageComposer {

    private List<EntityState> states;

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
    public void write() {

        this.response.init(Outgoing.UserStatusMessageComposer);
        this.response.writeInt(this.states.size());

        for (EntityState entity : this.states) {
            this.response.writeObject(entity);
        }
    }
}
