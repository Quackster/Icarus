package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.game.room.model.Rotation;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.util.Util;

import com.google.common.collect.Lists;

public class MovementTask implements Runnable {

    private Room room;

    public MovementTask(Room room) {
        this.room = room;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        if (this.room.getEntityManager().getEntities().size() == 0) {
            return;
        }

        List<Entity> entities = this.room.getEntityManager().getEntities();
        List<Entity> entitiesToUpdate = Lists.newArrayList();

        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);

            if (entity != null) {
                if (entity.getRoomUser() != null) {

                    this.processEntity(entity);

                    RoomUser roomEntity = entity.getRoomUser();

                    if (roomEntity.getNeedsUpdate()) {
                        roomEntity.setNeedsUpdate(false);
                        entitiesToUpdate.add(entity);
                    }
                }
            }
        }

        if (entitiesToUpdate.size() > 0) {
            this.room.send(new UserStatusMessageComposer(entitiesToUpdate));
        }
    }

    /**
     * Process entity.
     *
     * @param entity the entity
     */
    private void processEntity(Entity entity) {

        RoomUser roomUser = entity.getRoomUser();

        Position position = roomUser.getPosition();
        Position goal = roomUser.getGoal();

        if (roomUser.isWalking()) {

            // Apply next tile from the tile we removed from the list the cycle before
            if (roomUser.getNextPosition() != null) {
                roomUser.getPosition().setX(roomUser.getNextPosition().getX());
                roomUser.getPosition().setY(roomUser.getNextPosition().getY());
                roomUser.updateNewHeight(roomUser.getNextPosition());
            }

            // We still have more tiles left, so lets continue moving
            if (roomUser.getPath().size() > 0) {

                Position next = roomUser.getPath().pop();

                if (!roomUser.getRoom().getMapping().isTileWalkable(next.getX(), next.getY(), entity)) {
                    roomUser.walkTo(goal.getX(), goal.getY()); // Tile was invalid after we started walking, so lets try again!
                    this.processEntity(entity);
                    return;
                }

                RoomTile previousTile = roomUser.getTile();
                RoomTile nextTile = roomUser.getRoom().getMapping().getTile(next.getX(), next.getY());

                previousTile.removeEntity(entity);
                nextTile.addEntity(entity);

                roomUser.removeStatus(EntityStatus.LAY);
                roomUser.removeStatus(EntityStatus.SIT);

                int rotation = Rotation.calculate(position.getX(), position.getY(), next.getX(), next.getY());
                double height = this.room.getMapping().getTileHeight(next.getX(), next.getY());

                roomUser.getPosition().setRotation(rotation);
                roomUser.setStatus(EntityStatus.MOVE, next.getX() + "," + next.getY() + "," + Util.format(height));
                roomUser.setNextPosition(next);
            } else {

                // No more tiles left, so lets stop walking and interact with any furniture nearby
                roomUser.setNextPosition(null);
                roomUser.setWalking(false);
                roomUser.removeStatus(EntityStatus.MOVE);
                roomUser.checkNearbyItem();

            }

            // If we're walking, make sure to tell the server
            roomUser.setNeedsUpdate(true);
        }
    }
}