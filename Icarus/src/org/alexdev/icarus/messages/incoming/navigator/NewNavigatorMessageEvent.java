package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.navigator.FlatCategoriesMessageComposer;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorCategories;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorMetaDataComposer;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public class NewNavigatorMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {

		/*Response response = new Response();
		response.init(Outgoing.NavigatorLiftedRoomsComposer);
		response.appendInt32(0);
		player.send(response);

		response.init(Outgoing.NavigatorSavedSearchesComposer);
		response.appendInt32(1);
		response.appendInt32(1);
		response.appendString("myworld_view");
		response.appendString("test3");
		response.appendString("");
		player.send(response);
		
		response.init(Outgoing.NewNavigatorSizeMessageComposer);
		response.appendInt32(50);//pref.NewnaviX);
		response.appendInt32(50);//pref.NewnaviY);
		response.appendInt32(580);//pref.NewnaviWidth);
		response.appendInt32(600);//pref.NewnaviHeight);
		response.appendBoolean(true);
		response.appendInt32(1);
		player.send(response);*/

		player.send(new NavigatorMetaDataComposer(NavigatorManager.getParentTabs()));
		player.send(new FlatCategoriesMessageComposer(NavigatorManager.getPrivateRoomCategories()));
		player.send(new NavigatorCategories(NavigatorManager.getPrivateRoomCategories()));

	}

}
