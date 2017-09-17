package org.alexdev.icarus.game.room.tasks;

import java.util.ArrayList;
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

public class MovementTask implements Runnable {

    private Room room;

    public MovementTask(Room room) {
        this.room = room;
    }

    @Override
    public void run() {

        if (this.room.getEntityManager().getEntities().size() == 0) {
            return;
        }

        List<Entity> entities = this.room.getEntityManager().getEntities();
        List<Entity> entitiesToUpdate = new ArrayList<Entity>();

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

    private void processEntity(Entity entity) {

        RoomUser roomUser = entity.getRoomUser();

        Position position = roomUser.getPosition();
        Position goal = roomUser.getGoal();

        if (roomUser.isWalking()) {
            if (roomUser.getPath().size() > 0) {      
                Position next = roomUser.getPath().pop();

                if (!roomUser.getRoom().getMapping().isTileWalkable(entity, next.getX(), next.getY())) {
                    roomUser.walkTo(goal.getX(), goal.getY());
                    this.processEntity(entity);
                    return;
                }

                RoomTile nextTile = roomUser.getRoom().getMapping().getTile(next.getX(), next.getY());
                RoomTile previousTile = roomUser.getRoom().getMapping().getTile(position.getX(), position.getY());

                previousTile.setEntity(null);
                nextTile.setEntity(entity);   

                roomUser.removeStatus(EntityStatus.LAY);
                roomUser.removeStatus(EntityStatus.SIT);

                int rotation = Rotation.calculate(position.getX(), position.getY(), next.getX(), next.getY());
                double height = this.room.getMapping().getTileHeight(next.getX(), next.getY());

                roomUser.getPosition().setRotation(rotation);
                roomUser.setStatus(EntityStatus.MOVE, next.getX() + "," + next.getY() + "," + Util.getDecimalFormatter().format(height));

                roomUser.setNext(next);
            } else {
                roomUser.setNext(null);
            }

            roomUser.setNeedsUpdate(true);
        }
    }
}