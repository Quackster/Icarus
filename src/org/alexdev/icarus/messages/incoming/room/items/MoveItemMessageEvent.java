package org.alexdev.icarus.messages.incoming.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pathfinder.AffectedTile;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.messages.outgoing.room.items.MoveItemMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MoveItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        if (!player.inRoom()) {
            return;
        }

        Room room = player.getRoom();

        if (!room.hasRights(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }

        Item item = room.getItemManager().getItem(reader.readInt());

        if (item == null) {
            return;
        }

        boolean rotation = false;

        if (item.getDefinition().getType() == ItemType.FLOOR) {

            int x = reader.readInt();
            int y = reader.readInt();
            int direction = reader.readInt();

            if (item.getPosition().getX() == x && item.getPosition().getY() == y) {
                rotation = true;
            } else {

                if (!item.isWalkable(false)) {
                    
                    List<Position> positions = AffectedTile.getAffectedTiles(item.getDefinition().getLength(), item.getDefinition().getWidth(), x, y, direction);
                    positions.add(new Position(x, y));

                    for (Position position : positions) {
                        RoomTile tile = room.getMapping().getTile(position.getX(), position.getY());

                        if (tile.getEntities().size() > 0) {
                            
                            // Oops! Can't move on top of users, sorry!
                            player.send(new MoveItemMessageComposer(item));
                            
                            return;
                        }
                    }
                }
            }

            item.getPosition().setX(x);
            item.getPosition().setY(y);
            item.getPosition().setRotation(direction);
        } 

        if (item.getDefinition().getType() == ItemType.WALL) {
            String input = reader.readString();
            String[] pos = input.split(":")[1].split(" ");
            item.parseWallPosition(pos[2] + "," + pos[0].substring(2) + " " + pos[1].substring(2));

        }

        room.getMapping().updateItemPosition(item, rotation);
    }
}
