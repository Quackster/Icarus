package org.alexdev.icarus.game.player;

import java.util.List;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.player.club.ClubSubscription;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.BroadcastMessageAlertComposer;
import org.alexdev.icarus.server.api.IPlayerNetwork;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class Player extends Entity {

    private String machineId;
    private PlayerDetails details;
    private IPlayerNetwork network;
    private RoomUser roomUser;
    private Messenger messenger;
    private Inventory inventory;
    private ClubSubscription subscription;
    
    private boolean loggedIn;

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

        // Add player to logged in maps
        PlayerManager.addPlayer(this);
        
        // Load all player rooms into memory
        RoomDao.getPlayerRooms(this.details.getId(), true);

        // Load all inventory items
        this.inventory.init();
        this.messenger.init();
    }

    public void leaveRoom(boolean hotelView) {

        if (this.roomUser.getRoom() != null) {
            return;
        }
        
        if (hotelView) {
            this.send(new HotelViewMessageComposer());
        }

        PluginManager.callEvent(PluginEvent.ROOM_LEAVE_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(this), CoerceJavaToLua.coerce(this.roomUser.getRoom()) });

        this.roomUser.getRoom().removeEntity(this);
        this.roomUser.getRoom().dispose(false);

        this.messenger.sendStatus(false);
    }
    
    public void sendMessage(String message) {
        this.send(new BroadcastMessageAlertComposer(message));
    }

    @Override
    public void dispose() {

        if (!this.details.isAuthenticated()) {
            return;   
        }

        if (this.roomUser.getRoom() != null) {
            this.leaveRoom(false);
        }

        PluginManager.callEvent(PluginEvent.PLAYER_DISCONNECT_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(this) });

        for (Room room : RoomManager.getPlayerRooms(this.details.getId())) {
            room.dispose(false); 
        }
        
        PlayerManager.removePlayer(this);

        this.messenger.dispose();
        this.roomUser.dispose();
        this.inventory.dispose();
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

    public void send(MessageComposer response) {
        this.network.send(response);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
