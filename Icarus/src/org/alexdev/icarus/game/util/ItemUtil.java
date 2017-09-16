package org.alexdev.icarus.game.util;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.server.api.messages.Response;

public class ItemUtil {

    /**
     * Generate extra data depending on the type of item.
     *
     * @param item the item
     * @param response the response
     */
    public static void generateExtraData(Item item, Response response) {

        String extraData = item.getExtraData();

        if (item.getDefinition().isAdsFurni()) {

            response.writeInt(0);
            response.writeInt(1);

            if (!extraData.equals("")) {
                String[] adsData = extraData.split(Character.toString((char) 9));
                int count = adsData.length;

                response.writeInt(count / 2);

                for (int i = 0; i <= count - 1; i++) {
                    response.writeString(adsData[i]);
                }
                
            } else {
                response.writeInt(0);
            }
            
        } else {

            response.writeInt(1);
            response.writeInt(0);
            response.writeString(extraData);
        }
    }
}
