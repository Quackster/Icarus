package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FlatCategoriesMessageComposer implements OutgoingMessageComposer {

	private String[] categories;

	public FlatCategoriesMessageComposer(String[] categories) {
		this.categories = categories;
	}

	@Override
	public void write(Response response) {
		
		response.init(Outgoing.FlatCategoriesMessageComposer);
		response.writeInt(this.categories.length);

		int index = 0;
		
		for (String category : this.categories) {
			response.writeInt(index);
			response.writeString(category);
			response.writeBool(true); // show category?
			response.writeBool(false); // no idea
			response.writeString("NONE");
			response.writeString("");
			response.writeBool(false);
			
			++index;
		}
	}
}
