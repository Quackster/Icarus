package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.navigator.FlatCategoriesMessageComposer;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorCategories;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorMetaDataComposer;
import org.alexdev.icarus.messages.outgoing.navigator.NavigatorPreferencesComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class NewNavigatorMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        /*Response response = new Response();
        this.response.init(Outgoing.NavigatorLiftedRoomsComposer);
        this.response.appendInt32(0);
        player.send(response);

        this.response.init(Outgoing.NavigatorSavedSearchesComposer);
        this.response.appendInt32(1);
        this.response.appendInt32(1);
        this.response.appendString("myworld_view");
        this.response.appendString("test3");
        this.response.appendString("");
        player.send(response);
        
        this.response.init(Outgoing.NewNavigatorSizeMessageComposer);
        this.response.appendInt32(50);//pref.NewnaviX);
        this.response.appendInt32(50);//pref.NewnaviY);
        this.response.appendInt32(580);//pref.NewnaviWidth);
        this.response.appendInt32(600);//pref.NewnaviHeight);
        this.response.appendBoolean(true);
        this.response.appendInt32(1);
        player.send(response);*/

        player.send(new NavigatorMetaDataComposer(NavigatorManager.getParentTabs()));
        player.send(new FlatCategoriesMessageComposer(NavigatorManager.getCategories()));
        player.send(new NavigatorCategories(NavigatorManager.getCategories()));
        player.send(new NavigatorPreferencesComposer());

    }

}
