package org.alexdev.icarus.game.room.tasks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.interactions.InteractionType;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.game.room.scheduler.RoomTask;
import org.alexdev.icarus.messages.outgoing.room.items.SlideObjectMessageComposer;

public class RollerTask extends RoomTask {

    private Room room;

    public RollerTask(Room room) {
        this.room = room;
    }

    @Override
    public void execute() {

        boolean redoMap = false;

        if (!(this.room.getEntityManager().getEntities().size() > 0)) {
            return;
        }

        if (!this.room.getItemManager().hasRollers()) {
            return;
        }

        List<Item> rollers = this.room.getItemManager().getRollers();
        List<Entity> entities = this.room.getEntityManager().getEntities();

        List<Object> blacklist = new ArrayList<>();

        for (Item roller : rollers) {

            Set<Item> items = roller.getTile().getItems();

            for (Item item : items) {

                if (item.getPosition().equals(roller.getPosition()) && item.getPosition().getZ() > roller.getPosition().getZ()) {

                    if (blacklist.contains(item)) {
                        continue;
                    }

                    Position front = roller.getPosition().getSquareInFront();

                    if (!this.room.getMapping().isTileWalkable(front.getX(), front.getY(), null)) {
                        continue;
                    }

                    blacklist.add(item);

                    RoomTile frontTile = this.room.getMapping().getTile(front.getX(), front.getY());
                    double nextHeight = frontTile.getHeight();

                    if (frontTile.getHighestItem() != null) {
                        if (!frontTile.getHighestItem().getDefinition().isRoller()) {
                            if (item.getDefinition().allowStack() && item.getDefinition().getStackHeight() == frontTile.getHighestItem().getDefinition().getStackHeight()) {
                                nextHeight -= item.getDefinition().getStackHeight();
                            }

                        }
                    }

                    // If this item is stacked, we maintain its stack height
                    if (item.getItemBeneath() != null) {
                        if (!item.getItemBeneath().getDefinition().isRoller()) {
                            nextHeight = item.getPosition().getZ();

                            // If the next tile/front tile is not a roller, we need to adjust the sliding so the stacked items
                            // don't float, so we subtract the stack height of the roller
                            boolean subtractRollerHeight = false;

                            if (frontTile.getHighestItem() != null) {
                                if (!frontTile.getHighestItem().getDefinition().isRoller()) {
                                    subtractRollerHeight = true;
                                }
                            } else {
                                subtractRollerHeight = true;
                            }

                            if (subtractRollerHeight) {
                                nextHeight -= roller.getDefinition().getStackHeight();
                            }
                        }
                    }


                    room.send(new SlideObjectMessageComposer(item, front, roller.getId(), nextHeight));

                    item.getPosition().setX(front.getX());
                    item.getPosition().setY(front.getY());
                    item.getPosition().setZ(nextHeight);
                    item.save();

                    redoMap = true;
                }
            }

            for (int i = 0; i < entities.size(); i++) {

                Entity entity = entities.get(i);

                if (blacklist.contains(entity)) {
                    continue;
                }
                
                if (entity.getRoomUser().isWalking()) {
                    continue;
                }

                if (entity.getRoomUser().getPosition().equals(roller.getPosition()) && entity.getRoomUser().getPosition().getZ() > roller.getPosition().getZ()) {

                    Position front = roller.getPosition().getSquareInFront();

                    if (!this.room.getMapping().isValidStep(entity, entity.getRoomUser().getPosition(), front, false)) {
                        continue;
                    }

                    blacklist.add(entity);

                    RoomTile nextTile = this.room.getMapping().getTile(front.getX(), front.getY());
                    RoomTile previousTile = this.room.getMapping().getTile(entity.getRoomUser().getPosition().getX(), entity.getRoomUser().getPosition().getY());

                    previousTile.removeEntity(entity);
                    nextTile.addEntity(entity);
                    
                    double nextHeight = nextTile.getHeight();
                    this.room.send(new SlideObjectMessageComposer(entity, front, roller.getId(), nextHeight));
                    
                    entity.getRoomUser().interactNearbyItem();
                    entity.getRoomUser().getPosition().setX(front.getX());
                    entity.getRoomUser().getPosition().setY(front.getY());
                    entity.getRoomUser().getPosition().setZ(nextHeight);
                }
            }

            if (redoMap) {
                this.room.getMapping().regenerateCollisionMaps(false);
            }
        }
    }
}
