package org.alexdev.icarus.game.catalogue;

import java.util.ArrayList;
import java.util.List;

public class CatalogueTab {
    
    private int id;
    private int parentId;
    private String caption;
    private int iconImage;
    private boolean enabled;
    private int minRank;
    private String link;
    private List<CatalogueTab> childTabs;
    
    public CatalogueTab(int id, int parentId, String caption, int iconImage, boolean enabled, int minRank, String link) {
        this.id = id;
        this.parentId = parentId;
        this.caption = caption;
        this.iconImage = iconImage;
        this.enabled = enabled;
        this.minRank = minRank;
        this.childTabs = new ArrayList<>();
        this.link = link;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the parent id.
     *
     * @return the parent id
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * Gets the caption.
     *
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Gets the icon image.
     *
     * @return the icon image
     */
    public int getIconImage() {
        return iconImage;
    }

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Gets the min rank.
     *
     * @return the min rank
     */
    public int getMinRank() {
        return minRank;
    }

    /**
     * Gets the child tabs.
     *
     * @return the child tabs
     */
    public List<CatalogueTab> getChildTabs() {
        return childTabs;
    }
    
    /**
     * Gets the link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link.
     *
     * @param link the new link
     */
    public void setLink(String link) {
        this.link = link;
    }
}
