package org.alexdev.icarus.game.util;

import java.util.Map.Entry;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.extradata.ExtraData;
import org.alexdev.icarus.game.item.extradata.ExtraDataType;
import org.alexdev.icarus.game.item.extradata.types.IntArrayExtraData;
import org.alexdev.icarus.game.item.extradata.types.KeyValueExtraData;
import org.alexdev.icarus.game.item.extradata.types.StringArrayExtraData;
import org.alexdev.icarus.game.item.extradata.types.StringExtraData;
import org.alexdev.icarus.server.api.messages.Response;

public class ItemUtil {

    /**
     * Generate extra data depending on the type of item.
     *
     * @param item the item
     * @param response the response
     */
    public static void generateExtraData(Item item, Response response) {
        ExtraData extraData = item.getDefinition().getInteractionType().getInteractor().createExtraData(item);

        response.writeInt(extraData.getPerspective().getIdentifier());
        response.writeInt(extraData.getType().getTypeId());
        
        if (extraData.getType() == ExtraDataType.STRING) {
            StringExtraData data = (StringExtraData)extraData;
            response.writeString(data.getData());
        }
        
        if (extraData.getType() == ExtraDataType.STRING_ARRAY) {
            StringArrayExtraData data = (StringArrayExtraData)extraData;
            response.writeInt(data.getObjects().length);
            
            for (String str : data.getObjects()) {
                response.writeString(str);
            }
        }
        
        if (extraData.getType() == ExtraDataType.KEY_VALUE) {
            KeyValueExtraData data = (KeyValueExtraData)extraData;
            response.writeInt(data.getKeyValues().size());
            
            for (Entry<String, String> set : data.getKeyValues().entrySet()) {
                response.writeString(set.getKey());
                response.writeString(set.getValue());
            }
        }
        
        if (extraData.getType() == ExtraDataType.INT_ARRAY) {
            IntArrayExtraData data = (IntArrayExtraData)extraData;
            response.writeInt(data.getObjects().length);
            
            for (Integer num : data.getObjects()) {
                response.writeInt(num);
            }
        }
    }

    /**
     * Generate extra data for wall items depending on the type of item.
     *
     * @param item the item
     * @param response the response
     */
    public static void generateWallExtraData(Item item, Response response) {
        ExtraData extraData = item.getDefinition().getInteractionType().getInteractor().createExtraData(item);
        
        if (extraData.getType() == ExtraDataType.STRING) {
            StringExtraData data = (StringExtraData)extraData;
            response.writeString(data.getData());
        }
    }
}
