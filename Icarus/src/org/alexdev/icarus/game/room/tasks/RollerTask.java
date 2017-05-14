package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomTask;
import org.alexdev.icarus.game.room.RoomUser;
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

					//room.send(new SlideObjectMessageComposer());

					List<Item> items = this.room.getMapping().getTile(roller.getPosition().getX(), roller.getPosition().getY()).getItems();

					for (Item item : items) {	
						if (item.getPosition().isMatch(roller.getPosition()) && item.getPosition().getZ() > roller.getPosition().getZ()) {

							Position front = roller.getPosition().getSquareInFront();

							if (!this.room.getMapping().isTileWalkable(null, front.getX(), front.getY())) {
								continue;
							}

							double nextHeight = this.room.getMapping().getTile(front.getX(), front.getY()).getHeight();

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
							double nextHeight = entity.getRoomUser().getPosition().getZ();

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
