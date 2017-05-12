package org.alexdev.icarus.game.item;

import org.alexdev.icarus.server.api.messages.Response;

public interface ItemSerialise {

    public void serialiseInventory(Response response);
    public void serialiseItem(Response response);
}
