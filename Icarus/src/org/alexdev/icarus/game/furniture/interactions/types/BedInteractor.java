package org.alexdev.icarus.game.furniture.interactions.types;

import java.util.List;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.user.RoomUser;

import com.google.common.collect.Lists;

public class BedInteractor implements Interaction {

    @Override
    public void onUseItem(Item item, RoomUser roomUser) { }

    @Override
    public void onStopWalking(Item item, RoomUser roomUser) {

        if (isValidPillowTile(item, roomUser.getPosition())) {

            roomUser.getPosition().setRotation(item.getPosition().getRotation());
            roomUser.removeStatus(EntityStatus.DANCE);
            roomUser.removeStatus(EntityStatus.LAY);
            roomUser.setStatus(EntityStatus.LAY, "1.5");
            
        } else {

            for (Position tile : getValidPillowTiles(item)) {

                if (roomUser.getPosition().getX() != tile.getX()) {
                    roomUser.getPosition().setY(tile.getY());
                }

                if (roomUser.getPosition().getY() != tile.getY()) {
                    roomUser.getPosition().setX(tile.getX());
                }
            }
            
            roomUser.triggerCurrentItem();
            
        }
    }

    public static boolean isValidPillowTile(Item item, Position position) {

        if (item.getDefinition().getInteractionType() == InteractionType.BED) {

            if (item.getPosition().getX() == position.getX() && item.getPosition().getY() == position.getY()) {
                return true;
            } else {

                int validPillowX = -1;
                int validPillowY = -1;

                if (item.getPosition().getRotation() == 0) {
                    validPillowX = item.getPosition().getX() + 1;
                    validPillowY = item.getPosition().getY();
                }

                if (item.getPosition().getRotation() == 2) {
                    validPillowX = item.getPosition().getX();
                    validPillowY = item.getPosition().getY() + 1;
                }

                if (validPillowX == position.getX() && validPillowY == position.getY()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<Position> getValidPillowTiles(Item item) {

        List<Position> tiles = Lists.newArrayList();
        tiles.add(new Position(item.getPosition().getX(), item.getPosition().getY()));

        int validPillowX = -1;
        int validPillowY = -1;

        if (item.getPosition().getRotation() == 0) {
            validPillowX = item.getPosition().getX() + 1;
            validPillowY = item.getPosition().getY();
        }

        if (item.getPosition().getRotation() == 2) {
            validPillowX = item.getPosition().getX();
            validPillowY = item.getPosition().getY() + 1;
        }

        tiles.add(new Position(validPillowX, validPillowY));

        return tiles;
    }

}
