package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class NavigatorCategories implements OutgoingMessageComposer {
	
	private String[] categories;

	public NavigatorCategories(String[] categories) {
		this.categories = categories;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.NavigatorCategories);
		response.appendInt32(4 + this.categories.length);

		for (String category : this.categories) {
			response.appendString("category__" + category);
		}

		response.appendString("recommended");
		response.appendString("new_ads");
		response.appendString("staffpicks");
		response.appendString("official");
	}
}
