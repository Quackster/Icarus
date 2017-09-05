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
        
        int friendId = request.readInt();
        String message = request.readString();
        
        PlayerMessage msg = new PlayerMessage(player.getDetails().getId(), friendId, message);
        
        boolean isCancelled = PluginManager.callEvent(PluginEvent.MESSENGER_TALK_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(msg) });
        
        if (isCancelled) {
            return;
        }
        
        friendId = msg.getToId();
        message = msg.getMessage();
        
        if (!player.getMessenger().isFriend(friendId)) {
            return;
        }
        
        MessengerUser friend = player.getMessenger().getFriend(friendId);
        
        if (friend.isOnline()) {
            friend.getPlayer().send(new MessengerMessageComposer(player.getDetails().getId(), message));
        } else {
            // TODO: Offline messaging
        }
    }

}
