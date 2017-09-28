package org.alexdev.icarus.messages.incoming.groups;

import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.groups.GroupBadgeDialogComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GroupBadgeDialogMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        player.send(new GroupBadgeDialogComposer(
                GroupManager.getBases(), 
                GroupManager.getSymbols(), 
                GroupManager.getBaseColours(), 
                GroupManager.getSymbolColours(), 
                GroupManager.getBackgroundColours()));
    }

}
