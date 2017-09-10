package org.alexdev.icarus.game.navigator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorDao;

public class NavigatorManager {

    private static List<NavigatorTab> tabs;
    private static List<NavigatorCategory> categories;
    
    public static void load() throws Exception {
        tabs = NavigatorDao.getTabs(-1);
        categories = NavigatorDao.getCategories();
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
            return tabs.stream().filter(tab -> tab.getChildID() == -1).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }
    
    public static List<NavigatorCategory> getCategories() {
        return categories;
    }

    public static List<NavigatorTab> getAllTabs() {
        return tabs;
    }
}
