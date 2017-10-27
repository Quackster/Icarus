package org.alexdev.icarus.game.navigator;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorDao;
import org.alexdev.icarus.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NavigatorManager {


    private List<NavigatorTab> tabs;
    private List<NavigatorCategory> categories;

    private static final Logger log = LoggerFactory.getLogger(NavigatorManager.class);
    private static NavigatorManager instance;

    public NavigatorManager() {
        this.tabs = NavigatorDao.getTabs(-1);
        this.categories = NavigatorDao.getCategories();

        if (Configuration.getInstance().getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
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
    public NavigatorTab getTab(String tabName) {

        return this.tabs.stream().filter(tab -> tab.getTabName().equals(tabName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the parent tabs.
     *
     * @return the parent tabs
     */
    public List<NavigatorTab> getParentTabs() {
        return this.tabs.stream().filter(tab -> tab.getChildId() == -1).collect(Collectors.toList());
    }
    
    /**
     * Gets the categories.
     *
     * @return the categories
     */
    public List<NavigatorCategory> getCategories() {
        return this.categories;
    }

    /**
     * Gets the all tabs.
     *
     * @return the all tabs
     */
    public List<NavigatorTab> getAllTabs() {
        return this.tabs;
    }


    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static NavigatorManager getInstance() {

        if (instance == null) {
            instance = new NavigatorManager();
        }

        return instance;
    }
}
