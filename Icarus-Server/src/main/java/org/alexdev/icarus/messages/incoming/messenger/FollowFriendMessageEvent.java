package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.messenger.FollowBuddyMessageComposer;
import org.alexdev.icarus.messages.outgoing.messenger.FollowErrorMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class FollowFriendMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        int friendId = request.readInt();
        int errorId = -1;

        Player client = player.getMessenger().getFriend(friendId).getPlayer();
        
        if (client != null) {
            if (client.inRoom()) {
                player.send(new FollowBuddyMessageComposer(client.getRoom().getData().getId()));
            }

            else errorId = 2; // User is not in a room
        }
        else {
            errorId = 1; // User is offline
        }
        
        if (errorId != -1) {
            player.send(new FollowErrorMessageComposer(errorId));
        }
    }
}
