package org.alexdev.icarus.game.catalogue;

public class CatalogueTab {

	private int id;
	private int parentId;
	private String caption;
	private int iconColour;
	private int iconImage;
	private boolean enabled;
	private int minRank;
	
	public CatalogueTab() {
		
		
	}
	
	public void fill(int id, int parentId, String caption, int iconColour, int iconImage, boolean enabled, int minRank) {
		
		this.id = id;
		this.parentId = parentId;
		this.caption = caption;
		this.iconColour = iconColour;
		this.iconImage = iconImage;
		this.enabled = enabled;
		this.minRank = minRank;
	}

	public int getId() {
		return id;
	}

	public int getParentId() {
		return parentId;
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
}
