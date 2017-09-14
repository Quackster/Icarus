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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinimumRank() {
        return minimumRank;
    }

    public boolean isTradingAllowed() {
        return allowTrade;
    }
}
