package org.alexdev.icarus.game.catalogue;

import java.util.List;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.messages.outgoing.catalogue.CataloguePageMessageComposer;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class CataloguePage {

	private int id;
	private String layout;
	private String headline;
	private String teaser;
	private String special;
	private String text1;
	private String text2;
	private String textDetails;
	private String textTeaser;
	private boolean vip;
	private boolean loaded;
	private int minRank;
	private CataloguePageMessageComposer composer;
	
	public void fill(int id, String pageLayout, String pageHeadline, String pageTeaser, String pageSpecial, String pageText1, String pageText2, String pageTextDetails, String pageTextTeaser, boolean vip, int minRank) {
		this.id = id;
		this.layout = pageLayout;
		this.headline = pageHeadline;
		this.teaser = pageTeaser;
		this.special = pageSpecial;
		this.text1 = pageText1;
		this.text2 = pageText2;
		this.textDetails = pageTextDetails;
		this.textTeaser = pageTextTeaser;
		this.vip = vip;
		this.minRank = minRank;
		this.loaded = false;
	}

	public OutgoingMessageComposer getComposer() {
		
		if (!this.loaded) {
			this.composer = new CataloguePageMessageComposer(this, "NORMAL");
			this.loaded = true;
		}
		
		return composer;
	}
	
	public List<CatalogueItem> getItems() {
		return CatalogueManager.getPageItems(this.id);
	}
	
	public int getId() {
		return id;
	}

	public String getLayout() {
		return layout;
	}

	public String getHeadline() {
		return headline;
	}

	public String getTeaser() {
		return teaser;
	}

	public String getSpecial() {
		return special;
	}

	public String getText1() {
		return text1;
	}

	public String getText2() {
		return text2;
	}

	public String getTextDetails() {
		return textDetails;
	}

	public String getTextTeaser() {
		return textTeaser;
	}

	public boolean isVip() {
		return vip;
	}

	public int getMinRank() {
		return minRank;
	}



}
