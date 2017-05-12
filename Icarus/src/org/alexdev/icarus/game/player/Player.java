package org.alexdev.icarus.game.player;

import java.util.List;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.IPlayerNetwork;

public class Player extends Entity {

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


    public void login() {
        
        // Load all the rooms the player owns
        // Items and pets and other shit are only loaded when player enters the specific room
        RoomDao.getPlayerRooms(this.details, true);

        // Load their inventory data
        this.inventory.init();
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
