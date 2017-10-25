package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UserChangeMessageComposer extends MessageComposer {

    private Entity entity;
    private boolean self;

    public UserChangeMessageComposer(Entity entity, boolean self) {
        this.entity = entity;
        this.self = self;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UserChangeMessageComposer);
        response.writeInt(this.self ? -1 : this.entity.getRoomUser().getVirtualId());
        response.writeString(this.entity.getDetails().getFigure());
        response.writeString(this.entity.getDetails().getGender());
        response.writeString(this.entity.getDetails().getMission());
        response.writeInt(this.entity.getDetails().getAchievementPoints());
    }

    @Override
    public short getHeader() {
        return Outgoing.UserChangeMessageComposer;
    }
}
