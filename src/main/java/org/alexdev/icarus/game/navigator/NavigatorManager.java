package org.alexdev.icarus.game.navigator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorDao;
import org.alexdev.icarus.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavigatorManager {

    private static List<NavigatorTab> tabs;
    private static List<NavigatorCategory> categories;

    private static final Logger log = LoggerFactory.getLogger(NavigatorManager.class);
    
    public static void load() throws Exception {
        tabs = NavigatorDao.getTabs(-1);
        categories = NavigatorDao.getCategories();

        if (Util.getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} navigator categories", categories.size());
            log.info("Loaded {} navigator tabs", tabs.size());
        }
    }
    
    /**
     * Gets the tab.
     *
     * @param tabName the tab name
     * @return the tab
     */
    public static NavigatorTab getTab(String tabName) {

        Optional<NavigatorTab> navigatorTab = tabs.stream().filter(tab -> tab.getTabName().equals(tabName)).findFirst();

        if (navigatorTab.isPresent()) {
            return navigatorTab.get();
        } else {
            return null;
        }
    }

    /**
     * Gets the parent tabs.
     *
     * @return the parent tabs
     */
    public static List<NavigatorTab> getParentTabs() {

        try {
            
            return tabs.stream().filter(tab -> tab.getChildId() == -1).collect(Collectors.toList());
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Gets the categories.
     *
     * @return the categories
     */
    public static List<NavigatorCategory> getCategories() {
        return categories;
    }

    /**
     * Gets the all tabs.
     *
     * @return the all tabs
     */
    public static List<NavigatorTab> getAllTabs() {
        return tabs;
    }
}
