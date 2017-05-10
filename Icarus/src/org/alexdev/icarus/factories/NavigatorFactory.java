package org.alexdev.icarus.factories;

import org.alexdev.icarus.game.navigator.NavigatorTab;
import org.alexdev.icarus.game.room.populator.IRoomPopulator;
import org.alexdev.icarus.log.Log;

public class NavigatorFactory {
	
	public static NavigatorTab newTab() {
		NavigatorTab tab = new NavigatorTab();
		return tab;
	}
	
	public static NavigatorTab getTab(int id, int childId, String tabName, String title, byte buttonType, boolean closed, boolean thumbnail, String roomPopulator) {
		NavigatorTab tab = new NavigatorTab();
		tab.fill(id, childId, tabName, title, buttonType, closed, thumbnail, roomPopulator);
		return tab;
	}

	public static IRoomPopulator getPopulator(String roomPopulatorClass) {
		
		try {
			
			Class<? extends IRoomPopulator> clazz = Class.forName("org.alexdev.icarus.game.room.populator." + roomPopulatorClass).asSubclass(IRoomPopulator.class);
			return clazz.newInstance();
			
		} catch (Exception e) {
			Log.exception(e);
		}
		
		return null;
	}
}
