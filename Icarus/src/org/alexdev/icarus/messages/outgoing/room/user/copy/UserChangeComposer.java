package org.alexdev.icarus.messages.outgoing.room.user.copy;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class UserChangeComposer extends MessageComposer {

    private Entity entity;
    private boolean self;

    public UserChangeComposer(Entity entity, boolean self) {
        this.entity = entity;
        this.self = self;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.UserChangeComposer);
        this.response.writeInt(this.self ? -1 : this.entity.getRoomUser().getVirtualId());
        this.response.writeString(this.entity.getDetails().getFigure());
        this.response.writeString(this.entity.getDetails().getGender());
        this.response.writeString(this.entity.getDetails().getMission());
        this.response.writeInt(this.entity.getDetails().getAchievementPoints());
    }
}
