package org.alexdev.icarus.game.item.extradata;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.log.Log;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ExtraDataManager {

    private static final Gson gson = new Gson();
    
    /**
     * Gets the json data, will try restore to default if there's invalid JSON data.
     *
     * @param item the item
     * @param t the class type
     * @return the json data
     */
    public static <T> T getJsonData(Item item, Class<T> t) {
        T settings = null;
        
        if (isValidJSON(item.getExtraData(), t)) {
            settings = gson.fromJson(item.getExtraData(), t);
        } else {
            try {
                settings = t.getDeclaredConstructor().newInstance();
                
                saveExtraData(item, settings);
                item.saveExtraData();
                
            } catch (Exception e) {
            	Log.getErrorLogger().error("Could not deserialise JSON extradata for item {}: ", item.getId(), e);
            }
        }
        
        return settings;
    }
    
    /**
     * Save extra data to JSON format.
     *
     * @param item the item
     * @param src the data to serialise
     */
    public static <T> void saveExtraData(Item item, T src) {
        item.setExtraData(gson.toJson(src));
    }
    
    public static <T> boolean isValidJSON(String jsonInString, Class<T> obj) {
        try {
            
            gson.fromJson(jsonInString, obj);
            return true;
        } catch (JsonSyntaxException ex) { 
            return false;
        }
    }
}
