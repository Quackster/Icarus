package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class NavigatorCategories implements OutgoingMessageComposer {
	
	private String[] categories;

	public NavigatorCategories(String[] categories) {
		this.categories = categories;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.NavigatorCategories);
		response.writeInt(4 + this.categories.length);

		for (String category : this.categories) {
			response.writeString("category__" + category);
		}

		response.writeString("recommended");
		response.writeString("new_ads");
		response.writeString("staffpicks");
		response.writeString("official");
	}
}
