package org.alexdev.icarus.game.pathfinder;

import java.util.ArrayList;
import java.util.List;

public class AffectedTile {

    /**
     * Gets the affected tiles.
     *
     * @param length the length
     * @param width the width
     * @param posX the pos X
     * @param posY the pos Y
     * @param rotation the rotation
     * @return the affected tiles
     */
    public static List<Position> getAffectedTiles(int length, int width, int posX, int posY, int rotation) {
        
        List<Position> points = new ArrayList<Position>();

        if (length > 1)    {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < length; i++) {
                    points.add(new Position(posX, posY + i, i));

                    for (int j = 1; j < width; j++) {
                        points.add(new Position(posX + j, posY + i, (i < j) ? j : i));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < length; i++) {
                    points.add(new Position(posX + i, posY, i));

                    for (int j = 1; j < width; j++) {
                        points.add(new Position(posX + i, posY + j, (i < j) ? j : i));
                    }
                }
            }
        }

        if (width > 1) {
            if (rotation == 0 || rotation == 4) {
                for (int i = 1; i < width; i++) {
                    points.add(new Position(posX + i, posY, i));

                    for (int j = 1; j < length; j++) {
                        points.add(new Position(posX + i, posY + j, (i < j) ? j : i));
                    }
                }
            } else if (rotation == 2 || rotation == 6) {
                for (int i = 1; i < width; i++) {
                    points.add(new Position(posX, posY + i, i));

                    for (int j = 1; j < length; j++) {
                        points.add(new Position(posX + j, posY + i, (i < j) ? j : i));
                    }
                }
            }
        }
        
        return points;
    }
}