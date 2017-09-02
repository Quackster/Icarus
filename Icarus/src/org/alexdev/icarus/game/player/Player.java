package org.alexdev.icarus.game.player;

import java.util.List;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.player.club.ClubSubscription;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.messages.outgoing.user.BroadcastMessageAlertComposer;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.IPlayerNetwork;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Player extends Entity {

    private String machineId;
    private PlayerDetails details;
    private IPlayerNetwork network;
    private RoomUser roomUser;
    private Messenger messenger;
    private Inventory inventory;
    private ClubSubscription subscription;

    public Player(IPlayerNetwork network) {
        this.network = network;
        this.details = new PlayerDetails(this);
        this.roomUser = new RoomUser(this);
        this.messenger = new Messenger(this);
        this.inventory = new Inventory(this);
        this.subscription = new ClubSubscription(this);
    }

	public boolean hasPermission(String permission) {
		return PlayerManager.hasPermission(this.details.getRank(), permission);
	}

    public void login() {
    	
    	// Load all player rooms into memory
        RoomDao.getPlayerRooms(this.details, true);
        
        // Load all inventory items
        this.inventory.init();
    }
    
    public void sendMessage(String message) {
        this.send(new BroadcastMessageAlertComposer(message));
    }

    public void dispose() {

        if (this.details.isAuthenticated()) {

            if (this.roomUser.getRoom() != null) {
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
        this.roomUser.dispose();
        this.inventory.dispose();

        this.messenger = null;
        this.inventory = null;
        this.roomUser = null;
        this.machineId = null;
    }

    public List<Room> getRooms() {
        return RoomManager.getPlayerRooms(this.details.getId());
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

    public ClubSubscription getSubscription() {
        return subscription;
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
