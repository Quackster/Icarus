package org.alexdev.icarus.game.catalogue;

import java.util.List;

import com.google.common.collect.Lists;

public class CatalogueTab {

    private int id;
    private int parentID;
    private String caption;
    private int iconColour;
    private int iconImage;
    private boolean enabled;
    private int minRank;
    private String link;
    private List<CatalogueTab> childTabs;
    
    public CatalogueTab(int id, int parentID, String caption, int iconColour, int iconImage, boolean enabled, int minRank, String link) {
        
        this.id = id;
        this.parentID = parentID;
        this.caption = caption;
        this.iconColour = iconColour;
        this.iconImage = iconImage;
        this.enabled = enabled;
        this.minRank = minRank;
        this.childTabs = Lists.newArrayList();
        this.link = link;
    }

    public int getID() {
        return id;
    }

    public int getParentID() {
        return parentID;
    }

    public String getCaption() {
        return caption;
    }

    public int getIconColour() {
        return iconColour;
    }

    public int getIconImage() {
        return iconImage;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getMinRank() {
        return minRank;
    }

    public List<CatalogueTab> getChildTabs() {
        return childTabs;
    }
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
