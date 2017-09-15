package org.alexdev.icarus.game.navigator;

public class NavigatorCategory {
    
    private int id;
    private String name;
    private int minimumRank;
    private boolean allowTrade;

    public NavigatorCategory(int id, String name, int minimumRank) {
        this.id = id;
        this.name = name;
        this.minimumRank = minimumRank;
        this.allowTrade = name.toLowerCase().contains("trade") || name.toLowerCase().contains("trading") || name.toLowerCase().contains("shop");
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the minimum rank.
     *
     * @return the minimum rank
     */
    public int getMinimumRank() {
        return minimumRank;
    }

    /**
     * Checks if is trading allowed.
     *
     * @return true, if is trading allowed
     */
    public boolean isTradingAllowed() {
        return allowTrade;
    }
}
