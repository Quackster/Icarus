package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.messenger.PlayerMessage;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class MessengerTalkMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        int friendID = request.readInt();
        String message = request.readString();
        
        PlayerMessage msg = new PlayerMessage(player.getDetails().getID(), friendID, message);
        
        boolean isCancelled = PluginManager.callEvent(PluginEvent.MESSENGER_TALK_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(msg) });
        
        if (isCancelled) {
            return;
        }
        
        friendID = msg.getToID();
        message = msg.getMessage();
        
        if (!player.getMessenger().isFriend(friendID)) {
            return;
        }
        
        MessengerUser friend = player.getMessenger().getFriend(friendID);
        
        if (friend.isOnline()) {
            friend.getPlayer().send(new MessengerMessageComposer(player.getDetails().getID(), message));
        } else {
            // TODO: Offline messaging
        }
    }
}
