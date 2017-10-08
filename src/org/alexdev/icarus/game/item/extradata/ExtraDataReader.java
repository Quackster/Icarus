package org.alexdev.icarus.game.item.extradata;

import org.alexdev.icarus.game.item.Item;

import com.google.gson.Gson;

public abstract class ExtraDataReader<T> {

    public T getJsonData(Item item, Class<T> t) {
        T settings = new Gson().fromJson(item.getExtraData(), t);
        return settings;
    }
    
    public void saveExtraData(Item item, T src) {
        item.setExtraData(new Gson().toJson(src));
    }
}
