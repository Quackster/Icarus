package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomTask;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.items.SlideObjectMessageComposer;

public class RollerTask extends RoomTask {

	private Room room;

	public RollerTask(Room room) {
		this.room = room;
	}

	public void execute() {

		try {

			if (this.canTick(4)) {

				if (room.getEntities().size() == 0) {
					return;
				}

				List<Item> rollers = room.getItems(InteractionType.ROLLER);

				boolean reconstructMap = false;

				for (Entity entity : this.room.getEntities()) {

					if (entity.getRoomUser().isRolling()) {

						entity.getRoomUser().setRolling(false);
					}
				}

				for (Item roller : rollers) {

					List<Item> items = this.room.getMapping().getTile(roller.getPosition().getX(), roller.getPosition().getY()).getItems();

					for (Item item : items) {	
						if (item.getPosition().isMatch(roller.getPosition()) && item.getPosition().getZ() > roller.getPosition().getZ()) {

							Position front = roller.getPosition().getSquareInFront();

							if (!this.room.getMapping().isTileWalkable(null, front.getX(), front.getY())) {
								continue;
							}

							RoomTile frontTile = this.room.getMapping().getTile(front.getX(), front.getY());
							double nextHeight = frontTile.getHeight();

							// If this item is stacked, we maintain its stack height
							if (item.getItemUnderneath() != null) {
								if (item.getItemUnderneath().getDefinition().getInteractionType() != InteractionType.ROLLER) {
									nextHeight = item.getPosition().getZ();
									
									// If the next tile/front tile is not a roller, we need to adjust the sliding so the stacked items
									// don't float, so we subtract the stack height of the roller
									
									boolean subtractRollerHeight = false;
									
									if (frontTile.getHighestItem() != null) {
										if (frontTile.getHighestItem().getDefinition().getInteractionType() != InteractionType.ROLLER) {
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

							reconstructMap = true;

						}
					}

					for (Entity entity : this.room.getEntities()) {

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

							reconstructMap = true;
						}

					}
				}

				if (reconstructMap) {
					this.room.getMapping().regenerateCollisionMaps();
				}
			}
		} catch (Exception e) { e.printStackTrace(); }



		this.tick();
	}

}
