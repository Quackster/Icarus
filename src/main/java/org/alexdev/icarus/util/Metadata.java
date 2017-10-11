package org.alexdev.icarus.util;

import java.util.HashMap;
import java.util.Map;

public class Metadata {

    private Map<String, MetadataValue> data;
    
    public Metadata() {
        this.data = new HashMap<>();
    }
    
    /**
     * Get the instance of itself.
     * 
     * @return {@link Metadata}
     */
    public Metadata getMetadata() {
        return this;
    }
    
    /**
     * Sets a value in a Map, will override a value if the key exists.
     * 
     * @param key - the key
     * @param obj - the value
     */
    public void set(String key, Object obj) {
        data.put(key, new MetadataValue(obj, false));
    }
    

    /**
     * Sets the value in a Map, will override a value if the key exists.
     * Will also save the data in a database if allowSave is true;
     *
     * @param key - the key
     * @param obj - the value
     * @param allowSave should this value be saved to the database
     */
    public void set(String key, Object obj, boolean allowSave) {
        data.put(key, new MetadataValue(obj, allowSave));
    }
    
    /**
     * Returns a true if the key exists, false if otherwise.
     *
     * @param key {@link String}
     * @return {@link boolean} - true if exists
     */
    public boolean hasMetadata(String key) {
        return data.containsKey(key);
    }
    
    /**
     * Returns the object that was requested by key.
     *
     * @param key - {@link String}
     * @return {@link Object}
     */
    public Object get(String key) {
        return data.get(key).getObject();
    }

    /**
     * Returns the object that was requested by key, but will
     * return as the requested cast type.
     *
     * @param <T> the generic type
     * @param key - {@link String} the key
     * @param type - {@link Class} to cast to
     * @return {@link Object}
     */
    public <T> T get(String key, Class<T> type) {
        return type.cast(data.get(key).getObject());
    }
    
    /**
     * Tries to return the object that was requested by key as
     * an integer.
     * 
     * @param key - {@link String}
     * @return {@link Object}
     */
    public int getInt(String key) {
        return Integer.valueOf(data.get(key).getObject().toString());
    }
    
    /**
     * Tries to return the object that was requested by key as
     * a {@link String}.
     * 
     * @param key - {@link String}
     * @return {@link Object}
     */
    public String getString(String key) {
        return data.get(key).getObject().toString();
    }    

    /**
     * Tries to return the object that was requested by key as
     * a {@link boolean}. 
     * 
     * Will return false if the key does not exist.
     *
     * @param string the string
     * @return {@link Object}
     */
    public boolean getBoolean(String string) {
        
        if (!data.containsKey(string)) {
            return false;
        }
        
        return (boolean)data.get(string).getObject();
    }
    
    /**
     * Removes the entry from the map by key value.
     *
     * @param key - {@link String}
     */
    public void remove(String key) {
        data.remove(key);
    }

    /**
     * Clears the entire map.
     */
    public void clear() {
        data.clear();
    }

    /**
     * Gets the map.
     *
     * @return the map
     */
    public Map<String, MetadataValue> getMap() {
        return data;
    }
}
