package org.alexdev.icarus.game.room.tasks;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.game.room.model.Rotation;
import org.alexdev.icarus.game.room.scheduler.ScheduledTask;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.util.Util;

public class MovementTask implements ScheduledTask {

    private Room room;

    public MovementTask(Room room) {
        this.room = room;
    }

    @Override
    public void execute() {

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

                    if (roomEntity.needsUpdate()) {
                        entitiesToUpdate.add(entity);
                    }
                }
            }
        }

        if (entitiesToUpdate.size() > 0) {
            room.send(new UserStatusMessageComposer(entitiesToUpdate));
        }
    }

    private void processEntity(Entity entity) {

        RoomUser roomEntity = entity.getRoomUser();

        if (roomEntity.isWalking()) {
            if (roomEntity.getPath().size() > 0) {

                Position next = roomEntity.getPath().pop();

                if (!roomEntity.getRoom().getMapping().isTileWalkable(entity, next.getX(), next.getY())) {

                    // Invalid tile so lets try again
                    roomEntity.walkTo(roomEntity.getWalkingGoal().getX(), roomEntity.getWalkingGoal().getY());
                    this.processEntity(entity);
                    return;
                }

                RoomTile nextTile = roomEntity.getRoom().getMapping().getTile(next.getX(), next.getY());
                RoomTile previousTile = roomEntity.getRoom().getMapping().getTile(roomEntity.getPosition().getX(), roomEntity.getPosition().getY());

                previousTile.setEntity(null);
                nextTile.setEntity(entity);   

                roomEntity.removeStatus(EntityStatus.LAY);
                roomEntity.removeStatus(EntityStatus.SIT);

                int rotation = Rotation.calculate(roomEntity.getPosition().getX(), roomEntity.getPosition().getY(), next.getX(), next.getY());
                double height = this.room.getMapping().getTile(next.getX(), next.getY()).getHeight();

                roomEntity.getPosition().setRotation(rotation);
                roomEntity.setStatus(EntityStatus.MOVE, next.getX() + "," + next.getY() + "," + Util.getDecimalFormatter().format(height));

                roomEntity.setNext(next);
            }
            else {
                roomEntity.setNext(null);
            }

            roomEntity.setNeedUpdate(true);
        }
    }
}