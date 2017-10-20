package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
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
    public void compose(Response response) {
        //response.init(Outgoing.SlideObjectMessageComposer);
        response.writeInt(this.from.getX());
        response.writeInt(this.from.getY());
        response.writeInt(this.to.getX());
        response.writeInt(this.to.getY());

        if (this.item != null) {
            response.writeInt(1);
            response.writeInt(this.item.getId());
        } else {
            response.writeInt(0);
            response.writeInt(this.rollerId);
            response.writeInt(2);
            response.writeInt(this.entity.getRoomUser().getVirtualId());
        }
        response.writeString(Util.format(this.from.getZ()));
        response.writeString(Util.format(this.nextHeight));

        if (this.item != null) {
            response.writeInt(this.rollerId);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.SlideObjectMessageComposer;
    }
}