package org.alexdev.icarus.game.navigator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.dao.mysql.NavigatorDao;

public class NavigatorManager {

	private static List<NavigatorTab> tabs;
	
	public static void load() throws Exception {
		tabs = NavigatorDao.getTabs(-1);
	}
	
	public static NavigatorTab getTab(String tabName) {

		Optional<NavigatorTab> navigatorTab = tabs.stream().filter(tab -> tab.getTabName().equals(tabName)).findFirst();

		if (navigatorTab.isPresent()) {
			return navigatorTab.get();
		} else {
			return null;
		}
	}

	public static List<NavigatorTab> getParentTabs() {

		try {
			return tabs.stream().filter(tab -> tab.getChildId() == -1).collect(Collectors.toList());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String[] getPrivateRoomCategories() {
		return new String[] { "No Category", "School, Daycare & Adoption Rooms", "Help Centre, Guide & Service Rooms", "Hair Salons & Modelling Rooms", "Gaming & Race Rooms", "Trading & Shopping Rooms", "Maze & Theme Park Rooms", "Chat, Chill & Discussion Rooms", "Club & Group Rooms", "Restaurant, Bar & Night Club Rooms", "Themed & RPG Rooms", "Staff Rooms" };
	}

	public static List<NavigatorTab> getAllTabs() {
		return tabs;
	}
}
