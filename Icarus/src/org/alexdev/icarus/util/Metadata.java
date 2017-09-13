package org.alexdev.icarus.util;

import java.util.Map;

import com.google.common.collect.Maps;

public abstract class Metadata {

    private Map<String, Object> data;
    
    public Metadata() {
        this.data = Maps.newHashMap();
    }
    
    public Metadata getMetadata() {
        return this;
    }
    
    public void set(String key, Object obj) {
        this.data.put(key, obj);
    }
    
    public boolean hasMetadata(String key) {
        return this.data.containsKey(key);
    }
    
    public Object get(String key) {
        return this.data.get(key);
    }
    
    public int getAsInt(String key) {
        return Integer.valueOf(this.data.get(key).toString());
    }
    
    public String getAsString(String key) {
        return this.data.get(key).toString();
    }    

    public void remove(String key) {
        this.data.remove(key);
    }

    public boolean getAsBool(String string) {
        
        if (!this.data.containsKey(string)) {
            return false;
        }
        
        return (boolean)this.data.get(string);
    }

    public void clear() {
        this.data.clear();
        
    }
}
