package org.alexdev.icarus.game.catalogue;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.catalogue.CatalogueDao;
import org.alexdev.icarus.dao.mysql.catalogue.TargetedOfferDao;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CatalogueManager {

    private static List<CatalogueTab> parentTabs;
    private static Map<Integer, List<CatalogueTab>> childTabs;

    private static List<CataloguePage> pages;
    private static List<CatalogueItem> items;
    
    private static Map<Integer, TargetedOffer> offers;

    public static void load() {
        loadCataloguePages();
        loadCatalogueItems();

        offers = TargetedOfferDao.getOffers();
        parentTabs = CatalogueDao.getCatalogTabs(-1);
        childTabs = Maps.newHashMap();

        for (CatalogueTab parent : parentTabs) {
            loadCatalogueTabs(parent, parent.getId());
        }
    }
    
    public static void reloadOffers() {
        offers.clear();
        offers = TargetedOfferDao.getOffers();
    }

    public static void loadCatalogueTabs(CatalogueTab tab, int parent_id) {

        List<CatalogueTab> child = CatalogueDao.getCatalogTabs(tab.getId());

        if (child.size() > 0) { 

            for (CatalogueTab parent_tab : child) {
                tab.getChildTabs().add(parent_tab);
                loadCatalogueTabs(parent_tab, parent_tab.getId());
            }
        }
    }

    private static void loadCataloguePages() {
        pages = CatalogueDao.getCataloguePages();
    }


    private static void loadCatalogueItems() {
        items = CatalogueDao.getCatalogueItems();
    }

    public static List<CatalogueTab> getParentTabs(int rank) {
        return parentTabs.stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    public static List<CatalogueTab> getChildTabs(int parentId, int rank) {
        return childTabs.get(parentId).stream().filter(tab -> tab.getMinRank() <= rank).collect(Collectors.toList());
    }

    public static CataloguePage getPage(int pageId) {

        Optional<CataloguePage> cataloguePage = pages.stream().filter(page -> page.getId() == pageId).findFirst();

        if (cataloguePage.isPresent()) {
            return cataloguePage.get();
        } else {
            return null;
        }

    }

    public static List<CatalogueItem> getPageItems(int pageId) {

        try {
            return items.stream().filter(item -> item.getPageId() == pageId).collect(Collectors.toList());
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public static CatalogueItem getItem(int itemId) {

        Optional<CatalogueItem> catalogueItem = items.stream().filter(item -> item.getId() == itemId).findFirst();

        if (catalogueItem.isPresent()) {
            return catalogueItem.get();
        } else {
            return null;
        }

    }

    public List<CatalogueItem> getItems() {
        return items;
    }

    public static Collection<TargetedOffer> getOffers() {
        return offers.values();
    }
    
    public static TargetedOffer getOfferById(int id) {
        return offers.get(id);
    }
}
