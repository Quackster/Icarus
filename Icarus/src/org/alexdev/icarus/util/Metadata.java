package org.alexdev.icarus.util;

import java.util.Map;

public abstract class Metadata {

	private Map<String, Object> data;
	
	public void set(String key, Object obj) {
		this.data.put(key, obj);
	}
	
	public Object get(String key) {
		return this.data.get(key);
	}
	
	public void remove(String key) {
		this.data.remove(key);
	}
}
