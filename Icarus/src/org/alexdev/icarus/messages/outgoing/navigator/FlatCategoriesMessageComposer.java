package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class FlatCategoriesMessageComposer implements OutgoingMessageComposer {

	private String[] categories;

	public FlatCategoriesMessageComposer(String[] categories) {
		this.categories = categories;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.FlatCategoriesMessageComposer);
		response.appendInt32(this.categories.length);

		int index = 0;
		
		for (String category : this.categories) {
			response.appendInt32(index);
			response.appendString(category);
			response.appendBoolean(true); // show category?
			response.appendBoolean(false); // no idea
			response.appendString("NONE");
			response.appendString("");
			response.appendBoolean(false);
			
			++index;
		}
	}
}
