package org.alexdev.icarus.game.util;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.server.api.messages.Response;

public class ItemExtraDataUtil {

    public static void generateExtraData(Item item, Response response) {
        response.writeInt(1);
        response.writeInt(0);
        response.writeString(item.getExtraData());
    }
}
