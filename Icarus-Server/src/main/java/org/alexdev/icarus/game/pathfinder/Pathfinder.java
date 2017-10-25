package org.alexdev.icarus.game.pathfinder;

import java.util.Collections;
import java.util.LinkedList;

import org.alexdev.icarus.game.entity.Entity;

public class Pathfinder {

    public static final Position[] DIAGONAL_MOVE_POINTS = new Position[]{
            new Position(0, -1, 0),
            new Position(0, 1, 0),
            new Position(1, 0, 0),
            new Position(-1, 0, 0),
            new Position(1, -1, 0),
            new Position(-1, 1, 0),
            new Position(1, 1, 0),
            new Position(-1, -1, 0)
    };

    private static final Position[] MOVE_POINTS = new Position[]{
            new Position(0, -1),
            new Position(1, 0),
            new Position(0, 1),
            new Position(-1, 0)
    };
    
    /**
     * Make path.
     *
     * @param entity the entity
     * @return the linked list
     */
    public static LinkedList<Position> makePath(Entity entity) {

        int X = entity.getRoomUser().getGoal().getX();
        int Y = entity.getRoomUser().getGoal().getY();
        
        if (entity.getRoom().getModel().hasInvalidCoordinates(X, Y)) {
            return new LinkedList<>();
        }

        if (entity.getRoom().getModel().isBlocked(X, Y)) {
            return new LinkedList<>();
        }

        if (!entity.getRoom().getMapping().isTileWalkable(X, Y, entity)) {
            return new LinkedList<>();
        }

        if (entity.getRoomUser().getPosition().equals(new Position(X, Y))) {
            return new LinkedList<>();
        }
        
        LinkedList<Position> squares = new LinkedList<>();

        PathfinderNode nodes = makePathReversed(entity);

        if (nodes != null) {
            while (nodes.getNextNode() != null) {
                squares.add(new Position(nodes.getPosition().getX(), nodes.getPosition().getY()));
                nodes = nodes.getNextNode();
            }
        }
        
        Collections.reverse(squares);
        return squares;
    }

    /**
     * Make path reversed.
     *
     * @param entity the entity
     * @return the pathfinder node
     */
    private static PathfinderNode makePathReversed(Entity entity) {
        LinkedList<PathfinderNode> openList = new LinkedList<>();

        PathfinderNode[][] map = new PathfinderNode[entity.getRoom().getModel().getMapSizeX()][entity.getRoom().getModel().getMapSizeY()];
        PathfinderNode node;
        Position tmp;

        int cost;
        int diff;

        PathfinderNode current = new PathfinderNode(entity.getRoomUser().getPosition());
        current.setCost(0);

        Position end = entity.getRoomUser().getGoal();
        PathfinderNode finish = new PathfinderNode(end);

        map[current.getPosition().getX()][current.getPosition().getY()] = current;
        openList.add(current);

        Position[] POINTS = DIAGONAL_MOVE_POINTS;

        if (entity.getRoom().getMetadata().getBoolean("disableWalkDiagonal")) {
            POINTS = MOVE_POINTS;
        }

        while (openList.size() > 0) {
            current = openList.pollFirst();
            current.setInClosed(true);


            for (int i = 0; i < POINTS.length; i++) {
                tmp = current.getPosition().add(POINTS[i]);

                boolean isFinalMove = (tmp.getX() == end.getX() && tmp.getY() == end.getY());

                if (entity.getRoomUser().getRoom().getMapping().isValidStep(entity, new Position(current.getPosition().getX(), current.getPosition().getY(), current.getPosition().getZ()), tmp, isFinalMove)) {
                    if (map[tmp.getX()][tmp.getY()] == null) {
                        node = new PathfinderNode(tmp);
                        map[tmp.getX()][tmp.getY()] = node;
                    } else {
                        node = map[tmp.getX()][tmp.getY()];
                    }

                    if (!node.isInClosed()) {
                        diff = 0;

                        if (current.getPosition().getX() != node.getPosition().getX()) {
                            diff += 1;
                        }

                        if (current.getPosition().getY() != node.getPosition().getY()) {
                            diff += 1;
                        }

                        cost = current.getCost() + diff + node.getPosition().getDistanceSquared(end);

                        if (cost < node.getCost()) {
                            node.setCost(cost);
                            node.setNextNode(current);
                        }

                        if (!node.isInOpen()) {
                            if (node.getPosition().getX() == finish.getPosition().getX() && node.getPosition().getY() == finish.getPosition().getY()) {
                                node.setNextNode(current);
                                return node;
                            }

                            node.setInOpen(true);
                            openList.add(node);
                        }
                    }
                }
            }
        }

        return null;
    }
}