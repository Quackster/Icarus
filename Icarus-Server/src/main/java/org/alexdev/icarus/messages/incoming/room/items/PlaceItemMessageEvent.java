package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.locale.Locale;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.util.List;

public class PlaceItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player.getEntityId(), true) && !player.hasPermission("room_all_rights")) {
            return;
        }

        String input = reader.readString();

        String[] data = input.split(" ");
        int id = Integer.parseInt(data[0].replace("-", ""));

        Item item = player.getInventory().getItem(id);

        if (item == null) {
            return;
        }

        if (item.getDefinition().getInteractionType() == InteractionType.DIMMER) {

            List<Item> items = player.getRoom().getItemManager().getItems(InteractionType.DIMMER);

            if (items.size() > 0) {
                player.sendMessage(Locale.getInstance().getEntry("one.dimmer.per.room"));
                return;
            }
        }

        if (item.getDefinition().getType() == ItemType.WALL) {
            String[] pos = input.split(":")[1].split(" ");
            item.parseWallPosition(pos[2] + "," + pos[0].substring(2) + " " + pos[1].substring(2));
        }

        if (item.getDefinition().getType() == ItemType.FLOOR) {

            int x = Integer.parseInt(data[1]);
            int y = Integer.parseInt(data[2]);
            int rotation = Integer.parseInt(data[3]);

            if (!item.isWalkable()) {
                List<Position> positions = AffectedTile.getAffectedTiles(item.getDefinition().getLength(), item.getDefinition().getWidth(), x, y, rotation);
                positions.add(new Position(x, y));

                for (Position position : positions) {
                    RoomTile tile = room.getMapping().getTile(position.getX(), position.getY());

                    if (tile.getEntities().size() > 0) {
                        // Oops! Can't place on top of users, sorry!
                        return;
                    }
                }
            }

            double height = player.getRoomUser().getRoom().getModel().getHeight(x, y);

            item.getPosition().setX(x);
            item.getPosition().setY(y);
            item.getPosition().setZ(height);
            item.getPosition().setRotation(rotation);
        }

        room.getMapping().addItem(item);

        player.getInventory().remove(item);
        player.getInventory().updateItems();

        if (item.getDefinition().getType() == ItemType.FLOOR) {
            PluginManager.getInstance().callEvent(PluginEvent.PLACE_FLOOR_ITEM_EVENT, new LuaValue[] {
                    CoerceJavaToLua.coerce(player),
                    CoerceJavaToLua.coerce(item)
            });
        } 

        if (item.getDefinition().getType() == ItemType.WALL) {
            PluginManager.getInstance().callEvent(PluginEvent.PLACE_WALL_ITEM_EVENT, new LuaValue[] {
                    CoerceJavaToLua.coerce(player),
                    CoerceJavaToLua.coerce(item)
            });
        }
    }
}
