package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Util;

public class SlideObjectMessageComposer extends MessageComposer {

    private Item item;
    private Position from;
    private Position to;
    private int rollerId;
    private double nextHeight;
    private Entity entity;

    public SlideObjectMessageComposer(Item item, Position next, int rollerId, double nextHeight) {
        this.item = item;
        this.from = item.getPosition().copy();
        this.to = next.copy();
        this.rollerId = rollerId;
        this.nextHeight = nextHeight;
    }

    public SlideObjectMessageComposer(Entity entity, Position next, int rollerId, double nextHeight) {
        this.entity = entity;
        this.from = entity.getRoomUser().getPosition().copy();
        this.to = next.copy();
        this.rollerId = rollerId;
        this.nextHeight = nextHeight;
    }

    @Override
    public void write() {

        this.response.init(Outgoing.SlideObjectMessageComposer);
        this.response.writeInt(this.from.getX());
        this.response.writeInt(this.from.getY());
        this.response.writeInt(this.to.getX());
        this.response.writeInt(this.to.getY());

        if (this.item != null) {
            this.response.writeInt(1);
            this.response.writeInt(this.item.getId());
        } else {
            this.response.writeInt(0);
            this.response.writeInt(this.rollerId);
            this.response.writeInt(2);
            this.response.writeInt(this.entity.getRoomUser().getVirtualId());
        }
        this.response.writeString(Util.format(this.from.getZ()));
        this.response.writeString(Util.format(this.nextHeight));

        if (this.item != null) {
            this.response.writeInt(this.rollerId);
        }
    }
}
