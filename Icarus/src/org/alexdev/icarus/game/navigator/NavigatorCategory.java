package org.alexdev.icarus.game.navigator;

public class NavigatorCategory {

	private int id;
	private String name;
	private int minimumRank;
	
    public NavigatorCategory(int id, String name, int minimumRank) {
        this.id = id;
        this.name = name;
        this.minimumRank = minimumRank;
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
}
