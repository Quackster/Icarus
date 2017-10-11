package org.alexdev.icarus.game.catalogue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.catalogue.CatalogueDao;
import org.alexdev.icarus.dao.mysql.catalogue.TargetedOfferDao;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;

public class CatalogueManager {

    private static List<CatalogueTab> parentTabs;
    private static Map<Integer, List<CatalogueTab>> childTabs;
    
    private static List<CataloguePage> pages;
    private static List<CatalogueItem> items;
    private static Map<Integer, TargetedOffer> offers;

    /**
     * Load.
     */
    public static void load() {
        loadCataloguePages();
        loadCatalogueItems();

        offers = TargetedOfferDao.getOffers();
        parentTabs = CatalogueDao.getCatalogTabs(-1);
        childTabs = new HashMap<>();

        for (CatalogueTab parent : parentTabs) {
            loadCatalogueTabs(parent, parent.getId());
        }
    }
    
    /**
     * Reload offers.
     */
    public static void reloadOffers() {
        offers.clear();
        offers = TargetedOfferDao.getOffers();
    }

    /**
     * Load catalogue tabs.
     *
     * @param tab the tab
     * @param parent_id the parent id
     */
    public static void loadCatalogueTabs(CatalogueTab tab, int parent_id) {

        List<CatalogueTab> child = CatalogueDao.getCatalogTabs(tab.getId());

        if (child.size() > 0) { 

            for (CatalogueTab parent_tab : child) {
                tab.getChildTabs().add(parent_tab);
                loadCatalogueTabs(parent_tab, parent_tab.getId());
            }
        }
    }

    /**
     * Load catalogue pages.
     */
    private static void loadCataloguePages() {
        pages = CatalogueDao.getCataloguePages();
    }


    /**
     * Load catalogue items.
     */
    private static void loadCatalogueItems() {
        items = CatalogueDao.getCatalogueItems();
    }

    /**
     * Gets the parent tabs.
     *
     * @param rank the rank
     * @return the parent tabs
     */
    public static List<CatalogueTab> getParentTabs(int rank) {
        return parentTabs.stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    /**
     * Gets the child tabs.
     *
     * @param parentId the parent id
     * @param rank the rank
     * @return the child tabs
     */
    public static List<CatalogueTab> getChildTabs(int parentId, int rank) {
        return childTabs.get(parentId).stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    /**
     * Gets the page.
     *
     * @param pageId the page id
     * @return the page
     */
    public static CataloguePage getPage(int pageId) {

        Optional<CataloguePage> cataloguePage = pages.stream().filter(page -> page.getId() == pageId).findFirst();

        if (cataloguePage.isPresent()) {
            return cataloguePage.get();
        } else {
            return null;
        }

    }

    /**
     * Gets the page items.
     *
     * @param pageId the page id
     * @return the page items
     */
    public static List<CatalogueItem> getPageItems(int pageId) {

        try {
            return items.stream().filter(item -> item.getPageId() == pageId).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Gets the item.
     *
     * @param itemId the item id
     * @return the item
     */
    public static CatalogueItem getItem(int itemId) {

        Optional<CatalogueItem> catalogueItem = items.stream().filter(item -> item.getId() == itemId).findFirst();

        if (catalogueItem.isPresent()) {
            return catalogueItem.get();
        } else {
            return null;
        }

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
    public static Collection<TargetedOffer> getOffers() {
        return offers.values();
    }
    
    /**
     * Gets the offer by id.
     *
     * @param id the id
     * @return the offer by id
     */
    public static TargetedOffer getOfferById(int id) {
        return offers.get(id);
    }
}
