package org.alexdev.icarus.messages.outgoing.catalogue;

import java.util.List;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class CataloguePageMessageComposer implements OutgoingMessageComposer {

	private CataloguePage page;
	private String type;

	public CataloguePageMessageComposer(CataloguePage page, String type) {
		this.page = page;
		this.type = type;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.CataloguePageMessageComposer);
		response.appendInt32(this.page.getId());
		response.appendString(this.type);

		if (page.getLayout().toLowerCase().contains("frontpage")) {

			response.appendString("frontpage4");
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(2);
			response.appendString(page.getText1());
			response.appendString(page.getText2());

		} else if (page.equals("spaces") || page.equals("spaces_new")) {

			response.appendString("spaces_new");
			response.appendInt32(1);
			response.appendString(page.getHeadline());
			response.appendInt32(1);
			response.appendString(page.getText1());

		} else if (page.equals("default_3x3")) {

			response.appendString(page.getLayout());
			response.appendInt32(3);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendString(page.getSpecial());
			response.appendInt32(3);
			response.appendString(page.getText1());
			response.appendString(page.getTextDetails());
			response.appendString(page.getTextTeaser());

		} else if (page.equals("club_buy")) {

			response.appendString("vip_buy");
			response.appendString(page.getLayout());
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(0);

		} else if (page.equals("club_gifts")) {

			response.appendString("club_gifts");
			response.appendString(page.getHeadline());
			response.appendInt32(2);
			response.appendString(page.getText1());
			response.appendInt32(0);

		} else if (page.equals("recycler_info")) {

			response.appendString(page.getLayout());
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(3);
			response.appendString(page.getText1());
			response.appendString(page.getText2());
			response.appendString(page.getTextDetails());

		} else if (page.equals("recycler_prizes")) {

			response.appendString("recycler_prizes");
			response.appendInt32(1);
			response.appendString("catalog_header_furnimatic");
			response.appendInt32(1);
			response.appendString(page.getText1());

		} else if (page.equals("guilds")) {

			response.appendString("guild_frontpage");
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(3);
			response.appendString(page.getText1());
			response.appendString(page.getTextDetails());
			response.appendString(page.getTextTeaser());

		} else if (page.equals("guild_furni")) {

			response.appendString("guild_custom_furni");
			response.appendInt32(3);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendString(page.getSpecial());
			response.appendInt32(3);
			response.appendString(page.getText1());
			response.appendString(page.getTextDetails());
			response.appendString(page.getTextTeaser());

		} else if (page.equals("soundmachine")) {

			response.appendString("soundmachine");
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(2);
			response.appendString(page.getText1());
			response.appendString(page.getTextDetails());

		} else if (page.equals("pets")) {

			response.appendString("pets");
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(1);
			response.appendString(page.getText1());

		} else if (page.equals("bots")) {

			response.appendString(page.getLayout());
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(2);
			response.appendString(page.getText1());
			response.appendString(page.getTextDetails());

		} else if (page.equals("default_3x3_color_grouping")) {

			response.appendString(page.getLayout());
			response.appendInt32(2);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendInt32(2);
			response.appendString(page.getText1());
			response.appendString(page.getTextDetails());
		} else {

			response.appendString(page.getLayout());
			response.appendInt32(3);
			response.appendString(page.getHeadline());
			response.appendString(page.getTeaser());
			response.appendString(page.getSpecial());
			response.appendInt32(3);
			response.appendString(page.getText1());
			response.appendString(page.getTextDetails());
			response.appendString(page.getTextTeaser());
		}

		if (!page.getLayout().equals("frontpage") || !page.getLayout().equals("club_buy") || !page.getLayout().equals("guilds")) {

			List<CatalogueItem> items = page.getItems();

			response.appendInt32(items.size()); 

			for (CatalogueItem item : items) {
				item.serialise(response);
			}
		} else {
			response.appendInt32(0);
		}

		response.appendInt32(0);
		response.appendBoolean(false);
		
	}

}
