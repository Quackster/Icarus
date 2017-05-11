package org.alexdev.icarus.game.player;

import java.util.List;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.IPlayerNetwork;

public class Player implements IEntity {

	private String machineId;
	private PlayerDetails details;
	private IPlayerNetwork network;
	private RoomUser roomUser;
	private Messenger messenger;
	private Inventory inventory;

	public Player(IPlayerNetwork network) {

		this.network = network;
		this.details = new PlayerDetails(this);
		this.roomUser = new RoomUser(this);
		this.messenger = new Messenger(this);
		this.inventory = new Inventory(this);
	}

	public List<Room> getRooms() {
		return RoomManager.getPlayerRooms(this.details.getId());
	}
	
	public void dispose() {
		
		if (this.details.isAuthenticated()) {

			if (this.roomUser.inRoom()) {
				this.roomUser.getRoom().leaveRoom(this, false);
			}

			List<Room> rooms = RoomManager.getPlayerRooms(this.details.getId());

			if (rooms.size() > 0) {
				for (Room room : rooms) {
					room.dispose(false); 
				}
			}
		}
		
		this.messenger.dispose();
		this.messenger = null;

		this.roomUser.dispose();
		this.roomUser = null;

		this.details.dispose();
		this.details = null;
		
		this.inventory.dispose();
		this.inventory = null;

		this.machineId = null;
	}
	
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	public String getMachineId() {
		return machineId;
	}

	public PlayerDetails getDetails() {
		return details;
	}

	public RoomUser getRoomUser() {
		return roomUser;
	}

	public Messenger getMessenger() {
		return messenger;
	}

	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public EntityType getType() {
		return EntityType.PLAYER;
	}

	public IPlayerNetwork getNetwork() {
		return network;
	}

	public void send(OutgoingMessageComposer response) {
		this.network.send(response);
		
	}

}
