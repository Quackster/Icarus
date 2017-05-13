package org.alexdev.icarus.game.room.schedulers;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.game.room.model.Rotation;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;

public class RoomWalkScheduler implements Runnable {

    private Room room;

    public RoomWalkScheduler(Room room) {
        this.room = room;
    }

    @Override
    public void run() {

        try {

            if (this.room.getEntities().size() == 0) {
                return;
            }

            List<Entity> update_entities = new ArrayList<Entity>();
            List<Entity> entities = this.room.getEntities();

            for (int i = 0; i < entities.size(); i++) {

                Entity entity = entities.get(i);

                if (entity != null) {
                    if (entity.getRoomUser() != null) {

                        this.processEntity(entity);

                        RoomUser roomEntity = entity.getRoomUser();

                        if (roomEntity.needsUpdate()) {
                            update_entities.add(entity);
                        }
                    }
                }
            }

            if (update_entities.size() > 0) {
                room.send(new UserStatusMessageComposer(update_entities));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processEntity(Entity entity) {

        RoomUser roomEntity = entity.getRoomUser();

        if (roomEntity.isWalking()) {
            if (roomEntity.getPath().size() > 0) {

                Position next = roomEntity.getPath().pop();
                
                if (!roomEntity.getRoom().getMapping().isTileWalkable(entity, next.getX(), next.getY())) {
                    roomEntity.stopWalking();
                    return;
                }
                
                RoomTile nextTile = roomEntity.getRoom().getMapping().getTile(next.getX(), next.getY());
                nextTile.setEntity(entity);
                
                RoomTile previousTile = roomEntity.getRoom().getMapping().getTile(roomEntity.getPosition().getX(), roomEntity.getPosition().getY());
                previousTile.setEntity(null);

                roomEntity.removeStatus("lay");
                roomEntity.removeStatus("sit");

                int rotation = Rotation.calculate(roomEntity.getPosition().getX(), roomEntity.getPosition().getY(), next.getX(), next.getY());
                double height = this.room.getModel().getHeight(next.getX(), next.getY());
                          
                roomEntity.getPosition().setRotation(rotation);
                roomEntity.setStatus("mv", " " + next.getX() + "," + next.getY() + "," + Double.toString(height), true, -1);
                
                roomEntity.setNext(next);
                roomEntity.setNeedUpdate(true);
                
            }
            else {
                roomEntity.setNext(null);
                roomEntity.setNeedUpdate(true);
            }
        }
    }
}