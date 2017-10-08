package org.alexdev.icarus.game.item.extradata;

import org.alexdev.icarus.game.item.Item;

import com.google.gson.Gson;

public class ExtraDataReader {

    /**
     * Gets the json data.
     *
     * @param item the item
     * @param t the class type
     * @return the json data
     */
    public static <T> T getJsonData(Item item, Class<T> t) {
        T settings = new Gson().fromJson(item.getExtraData(), t);
        return settings;
    }
    
    /**
     * Save extra data to JSON format.
     *
     * @param item the item
     * @param src the data to serialise
     */
    public static <T> void saveExtraData(Item item, T src) {
        item.setExtraData(new Gson().toJson(src));
    }
}
