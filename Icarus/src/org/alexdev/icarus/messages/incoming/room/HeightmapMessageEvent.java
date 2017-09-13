package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomAction;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.NewGroupMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.FloorItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.WallItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.CarryObjectComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class HeightmapMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        if (room.getEntityManager().getEntities().contains(player)) {
            return;
        }

        player.send(new HeightMapMessageComposer(room.getModel()));
        player.send(new FloorMapMessageComposer(room));

        room.send(new UserDisplayMessageComposer(player));
        room.send(new UserStatusMessageComposer(player));

        if (!room.getEntityManager().getEntities().contains(player)) {
            room.getEntityManager().getEntities().add(player);
            room.getData().updateUsersNow();
        }

        player.send(new UserDisplayMessageComposer(room.getEntityManager().getEntities()));
        player.send(new UserStatusMessageComposer(room.getEntityManager().getEntities()));

        for (Player players : room.getEntityManager().getPlayers()) {
            if (players.getRoomUser().isDancing()) {
                player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
            }

            if (players.getRoomUser().getCarryItem() > 0) {
                player.send(new CarryObjectComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getCarryItem())); 
            }
        }

        if (player.hasPermission("room_all_rights") || room.getData().getOwnerId() == player.getDetails().getId()) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "4");
        } else if (room.hasRights(player, false)) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
        }        

        player.send(new WallOptionsMessageComposer(room.getData().hasHiddenWall(), room.getData().getWallThickness(), room.getData().getFloorThickness()));
        player.send(new RoomPromotionMessageComposer(room));
        player.send(new FloorItemsMessageComposer(room.getItemManager().getFloorItems()));
        player.send(new WallItemsMessageComposer(room.getItemManager().getWallItems()));
        player.send(new RoomDataMessageComposer(room, player, true, false));

        player.getMessenger().sendStatus(false);

        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_ENTER_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(this) });

        if (isCancelled) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }

        Group group = room.getGroup();

        if (group != null) {
            if (player.getRoomUser().getMetadata().getAsBool("showGroupHomeroomDialog")) {
                player.getRoomUser().getMetadata().set("showGroupHomeroomDialog", false);
                player.send(new NewGroupMessageComposer(room.getData().getId(), room.getData().getGroupId()));
            }
        }
    }
}