package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomTask;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.messages.outgoing.room.items.SlideObjectMessageComposer;

import com.google.common.collect.Lists;

public class RollerTask extends RoomTask {

    private Room room;

    public RollerTask(Room room) {
        this.room = room;
    }

    public void execute() {

        try {
            
            if (this.canTick(4)) {
                
                boolean redoMap = false;

                if (room.getEntities().size() == 0) {
                    return;
                }

                List<Item> rollers = room.getItems(InteractionType.ROLLER);
                List<Entity> entities = this.room.getEntities();

                for (int i = 0; i < entities.size(); i++) {

                    Entity entity = entities.get(i);

                    if (entity.getRoomUser().isRolling()) {
                        entity.getRoomUser().setRolling(false);
                    }
                }
                
                List<Item> rollingItems = Lists.newArrayList();

                for (Item roller : rollers) {

                    List<Item> items = this.room.getMapping().getTile(roller.getPosition().getX(), roller.getPosition().getY()).getItems();

                    for (int i = 0; i < items.size(); i++) {

                        Item item = items.get(i);

                        if (rollingItems.contains(item)) {
                            continue;
                        }
                        
                        if (item.getPosition().isMatch(roller.getPosition()) && item.getPosition().getZ() > roller.getPosition().getZ()) {

                            Position front = roller.getPosition().getSquareInFront();

                            if (!this.room.getMapping().isTileWalkable(null, front.getX(), front.getY())) {
                                continue;
                            }
                            
                            rollingItems.add(item);

                            RoomTile frontTile = this.room.getMapping().getTile(front.getX(), front.getY());
                            double nextHeight = frontTile.getHeight();

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
                                        nextHeight -= roller.getDefinition().getHeight();
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

                        if (entity.getRoomUser().isRolling()) {
                            continue;
                        }

                        if (entity.getRoomUser().isWalking()) {
                            continue;
                        }

                        if (entity.getRoomUser().getPosition().isMatch(roller.getPosition()) && entity.getRoomUser().getPosition().getZ() > roller.getPosition().getZ()) {
                            entity.getRoomUser().setRolling(true);

                            Position front = roller.getPosition().getSquareInFront();
                            double nextHeight = this.room.getMapping().getTile(front.getX(), front.getY()).getHeight();

                            if (!this.room.getMapping().isTileWalkable(entity, front.getX(), front.getY())) {
                                continue;
                            }

                            room.send(new SlideObjectMessageComposer(entity, front, roller.getId(), nextHeight));

                            entity.getRoomUser().getPosition().setX(front.getX());
                            entity.getRoomUser().getPosition().setY(front.getY());
                            entity.getRoomUser().getPosition().setZ(nextHeight);
                            entity.getRoomUser().setNeedUpdate(true);
                        }

                    }
                    
                    if (redoMap) {
                        this.room.getMapping().regenerateCollisionMaps();
                    }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        
        this.tick();

    }

}
