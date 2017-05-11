package org.alexdev.icarus.game.room;

import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.log.Log;

import com.google.common.collect.Maps;

public class RoomCycle implements Runnable {

	private int ticked = 0;
	private Map<RoomTask, Integer> events;

	public RoomCycle (Room room) {
		this.events = Maps.newHashMap();
	}

	@Override
	public void run() {

		try {
			
			for (Entry<RoomTask, Integer> set : this.events.entrySet()) {
				
				RoomTask event = set.getKey();
				int interval = set.getValue();
				
				if (interval > 0) {
					if (ticked % interval == 0) {
						event.execute();
					}
				} else {
					event.execute();
				}
			}
			
		} catch (Exception e) {
			Log.exception(e);

		}
		
		this.ticked++;
	}
	
	public void registerEvent(RoomTask task, int interval) {
		
		if (!this.events.containsKey(task)) {
			this.events.put(task, interval);
		}
	}
	
	public Map<RoomTask, Integer> getEvents() {
		return events;
	}

	public void dispose() {
		
		if (this.events != null) {
			this.events.clear();
		}
		
		this.events = null;
	}
}
