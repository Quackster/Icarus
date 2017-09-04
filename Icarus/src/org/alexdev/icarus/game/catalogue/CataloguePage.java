package org.alexdev.icarus.game.catalogue;

import java.util.List;

import org.alexdev.icarus.messages.outgoing.catalogue.CataloguePageMessageComposer;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class CataloguePage {

	private int id = -1;
	private String caption;
	private int parentId = -1;
	private String type;
	private String layout;
	private int minimum_rank;

	private List<String> images;
	private List<String> texts;
	private List<CatalogueItem> items = null;
	
	private CataloguePageMessageComposer composer = null;

	public CataloguePage(int id, String caption, int parentId, String type, String layout, int minimum_rank, List<String> images, List<String> texts, List<CatalogueItem> items) {
		super();
		this.id = id;
		this.caption = caption;
		this.parentId = parentId;
		this.type = type;
		this.layout = layout;
		this.minimum_rank = minimum_rank;
		this.images = images;
		this.texts = texts;
	}

	public CatalogueItem getItem(int itemId) {

		for (CatalogueItem item : this.items) {
			if (item.getId() == itemId) {
				return item;
			}
		}

		return null;
	}


	public MessageComposer getComposer() {

		if (this.composer == null) {
			this.composer = new CataloguePageMessageComposer(this, "NORMAL");
		}

		return composer;
	}

	public List<CatalogueItem> getItems() {

		if (this.items == null) {
			this.items = CatalogueManager.getPageItems(this.id);
		}

		return items;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @return the parentId
	 */
	public int getParentId() {
		return parentId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * @return the minimum_rank
	 */
	public int getMinRank() {
		return minimum_rank;
	}

	/**
	 * @return the images
	 */
	public List<String> getImages() {
		return images;
	}

	/**
	 * @return the texts
	 */
	public List<String> getTexts() {
		return texts;
	}



}
