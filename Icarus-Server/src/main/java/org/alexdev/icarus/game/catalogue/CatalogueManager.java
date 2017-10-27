package org.alexdev.icarus.game.catalogue;

import org.alexdev.icarus.dao.mysql.catalogue.CatalogueDao;
import org.alexdev.icarus.dao.mysql.catalogue.TargetedOfferDao;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;
import org.alexdev.icarus.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CatalogueManager {

    private List<CatalogueTab> parentTabs;
    private Map<Integer, List<CatalogueTab>> childTabs;
    
    private List<CataloguePage> pages;
    private List<CatalogueItem> items;
    private Map<Integer, TargetedOffer> offers;

    private static final Logger log = LoggerFactory.getLogger(CatalogueManager.class);
    private static CatalogueManager instance;


    public CatalogueManager() {
        this.reload();
    }

    /**
     * Reload the items from database again
     */
    public void reload() {
        this.loadCataloguePages();
        this.loadCatalogueItems();

        this.offers = TargetedOfferDao.getOffers();
        this.parentTabs = CatalogueDao.getCatalogTabs(-1);
        this.childTabs = new HashMap<>();

        for (CatalogueTab parent : parentTabs) {
            this.loadCatalogueTabs(parent);
        }

        if (Configuration.getInstance().getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} catalogue pages", pages.size());
            log.info("Loaded {} catalogue items", items.size());
        }
    }
    
    /**
     * Reload offers.
     */
    public void reloadOffers() {
        this.offers = TargetedOfferDao.getOffers();
    }

    /**
     * Load catalogue tabs.
     *
     * @param tab the tab
     */
    public void loadCatalogueTabs(CatalogueTab tab) {

        List<CatalogueTab> child = CatalogueDao.getCatalogTabs(tab.getId());

        for (CatalogueTab parentTab : child) {
            tab.getChildTabs().add(parentTab);
            loadCatalogueTabs(parentTab);
        }
    }

    /**
     * Load catalogue pages.
     */
    private void loadCataloguePages() {
        this.pages = CatalogueDao.getCataloguePages();
    }


    /**
     * Load catalogue items.
     */
    private void loadCatalogueItems() {
        this.items = CatalogueDao.getCatalogueItems();
    }

    /**
     * Gets the parent tabs.
     *
     * @param rank the rank
     * @return the parent tabs
     */
    public List<CatalogueTab> getParentTabs(int rank) {
        return this.parentTabs.stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    /**
     * Gets the child tabs.
     *
     * @param parentId the parent id
     * @param rank the rank
     * @return the child tabs
     */
    public List<CatalogueTab> getChildTabs(int parentId, int rank) {
        return this.childTabs.get(parentId).stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    /**
     * Gets the page.
     *
     * @param pageId the page id
     * @return the page
     */
    public CataloguePage getPage(int pageId) {

        return this.pages.stream().filter(page -> page.getId() == pageId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the page items.
     *
     * @param pageId the page id
     * @return the page items
     */
    public List<CatalogueItem> getPageItems(int pageId) {
        return this.items.stream().filter(item -> item.getPageId() == pageId).collect(Collectors.toList());
    }

    /**
     * Gets the item.
     *
     * @param itemId the item id
     * @return the item
     */
    public CatalogueItem getItem(int itemId) {

        return this.items.stream().filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets the items.
     *
     * @return the items
     */
    public List<CatalogueItem> getItems() {
        return items;
    }

    /**
     * Gets the offers.
     *
     * @return the offers
     */
    public Collection<TargetedOffer> getOffers() {
        return offers.values();
    }
    
    /**
     * Gets the offer by id.
     *
     * @param id the id
     * @return the offer by id
     */
    public TargetedOffer getOfferById(int id) {
        return offers.get(id);
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static CatalogueManager getInstance() {
        
        if (instance == null) {
            instance = new CatalogueManager();
        }
        
        return instance;
    }
}
