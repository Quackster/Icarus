package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomAction;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.OwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class EnterRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        int roomId = request.readInt();
        
        Room room = RoomDao.getRoom(roomId, true);
                
        if (room == null) {
            return;
        }
        
        player.getRoomUser().setRequestedRoomId(roomId);
        
        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_REQUEST_ENTER_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(room) });
        
        if (isCancelled) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, true);
            return;
        }
        
        if (player.getRoomUser().getRequestedRoomId() != roomId) {
            room.dispose(false);
            room = RoomDao.getRoom(player.getRoomUser().getRequestedRoomId(), true);
            
        }

        if (room == null) {
            return;
        }
        

        String password = request.readString();
        RoomUtil.entityRoomEntry(player, room, password);
    }


}
