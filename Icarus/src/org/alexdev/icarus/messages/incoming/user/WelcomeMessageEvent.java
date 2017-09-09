package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.item.TargettedOfferDataComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class WelcomeMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        if (player.isLoggedIn()) {
            return;
        }
        
        PluginManager.callEvent(PluginEvent.PLAYER_LOGIN_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player) });
        
        for (TargetedOffer offer : CatalogueManager.getOffers()) {
            player.send(new TargettedOfferDataComposer(offer));
        }
        
        player.setLoggedIn(true);
    }
}
