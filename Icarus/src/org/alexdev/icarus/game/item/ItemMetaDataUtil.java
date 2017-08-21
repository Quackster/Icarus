package org.alexdev.icarus.game.item;

import org.alexdev.icarus.server.api.messages.Response;

public class ItemMetaDataUtil {

    public static void generateExtraData(Item item, Response response) {
    	response.writeInt(1);
    	response.writeInt(0);
    	response.writeString(item.getExtraData());
    }
}
