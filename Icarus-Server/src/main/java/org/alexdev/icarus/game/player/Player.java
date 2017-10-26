package org.alexdev.icarus.game.player;

import io.netty.util.AttributeKey;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.encryption.DiffieHellman;
import org.alexdev.icarus.encryption.RC4;
import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.inventory.Inventory;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.player.club.ClubSubscription;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageHandler;
import org.alexdev.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.AvailabilityMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RoomForwardComposer;
import org.alexdev.icarus.messages.outgoing.user.*;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.PlayerNetwork;
import org.alexdev.icarus.util.Util;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Player extends Entity {

    public static final AttributeKey<Player> PLAYER_KEY = AttributeKey.valueOf("Player");

    private Logger logger;
	private String machineId;
    private PlayerDetails details;
    private PlayerNetwork network;
    private RoomUser roomUser;
    private Messenger messenger;
    private Inventory inventory;
    private ClubSubscription subscription;
    private RC4 rc4;
    private DiffieHellman diffieHellman;
    private MessageHandler messageHandler;

    private boolean loggedIn;

    public Player(PlayerNetwork network) {
        this.network = network;
        this.details = new PlayerDetails(this);
        this.roomUser = new RoomUser(this);
        this.messenger = new Messenger(this);
        this.inventory = new Inventory(this);
        this.subscription = new ClubSubscription(this);
        this.diffieHellman = new DiffieHellman();
        this.messageHandler = new MessageHandler(this);

        this.logger = LoggerFactory.getLogger("Player " + this.network.getConnectionId());
    }

    /**
     * Logs in the user.
     *
     * @param ssoTicket - single sign on ticket
     */
    public void authenticate(String ssoTicket) {

        boolean loginSuccess = PlayerDao.login(this, ssoTicket);
        //PlayerDao.clearTicket(this.getDetails().getId());

        if (GameSettings.BOT_SPAMMERS_ALLOW && ssoTicket.startsWith(GameSettings.BOT_SPAMMERS_SSO_PREFIX)) {
            loginSuccess = true;
            this.details.setId(Util.getRandom().nextInt(10000));
            this.details.setName("BottyBoi" + Util.getRandom().nextInt(10000));
        } else {
            if (!loginSuccess || this.getMachineId() == null || PlayerManager.getInstance().kickDuplicates(this)) {
                this.getNetwork().close();
                return;
            }
        }

        this.logger = LoggerFactory.getLogger("Player " + this.details.getName());

        PlayerManager.getInstance().addPlayer(this);
        RoomManager.getInstance().addRooms(RoomDao.getPlayerRooms(this.getEntityId()));

        this.getInventory().init();
        this.getMessenger().init();
        this.getDetails().setAuthenticated(true);

        this.sendQueued(new UniqueMachineIDMessageComposer(this.getMachineId()));
        this.sendQueued(new AuthenticationOKMessageComposer());
        this.sendQueued(this.getInventory().getEffectManager().createEffectsComposer());
        this.sendQueued(new HomeRoomMessageComposer(2, false));
        this.sendQueued(new LandingWidgetMessageComposer());
        this.sendQueued(new AvailabilityMessageComposer());
        this.flushQueue();
    }

    /**
     * Perform room action.
     *
     * @param action the action
     * @param value the value
     */
    public void performRoomAction(RoomAction action, Object value) {

        switch (action) {
        case LEAVE_ROOM: {

            Room room = this.roomUser.getRoom();
            boolean goHotelView = (boolean)value;

            if (room == null) {
                return;
            }

            PluginManager.getInstance().callEvent(PluginEvent.ROOM_LEAVE_EVENT, new LuaValue[] {
                CoerceJavaToLua.coerce(this), 
                CoerceJavaToLua.coerce(this.roomUser.getRoom()) 
            });

            if (goHotelView) {
                this.send(new HotelViewMessageComposer());
            }

            room.getEntityManager().removeEntity(this);
            room.dispose();

            this.messenger.sendStatus(false);
            break;

        }

        case FORWARD_ROOM: {
            int roomId = (int)value;
            this.send(new RoomForwardComposer(roomId));
            break;
        }
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#dispose()
     */
    @Override
    public void dispose() {

        if (!this.details.isAuthenticated()) {
            return;   
        }

        if (this.roomUser.getRoom() != null) {
            this.performRoomAction(RoomAction.LEAVE_ROOM, false);
        }

        PluginManager.getInstance().callEvent(PluginEvent.PLAYER_DISCONNECT_EVENT, new LuaValue[] {
            CoerceJavaToLua.coerce(this)
        });

        PlayerManager.getInstance().removePlayer(this);

        for (Room room : RoomManager.getInstance().getPlayerRooms(this.details.getId())) {
            room.dispose(); 
        }

        this.destroyObjects();
    }

    /**
     * Destroy objects.
     */
    private void destroyObjects() {
        this.network = null;
        this.details = null;
        this.roomUser = null;
        this.messenger = null;
        this.inventory = null;
        this.subscription = null;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#getType()
     */
    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }

    /**
     * Gets the rooms.
     *
     * @return the rooms
     */
    public List<Room> getRooms() {
        return RoomManager.getInstance().getPlayerRooms(this.details.getId());
    }

    /**
     * Sets the machine id.
     *
     * @param machineId the new machine id
     */
    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    /**
     * Gets the machine id.
     *
     * @return the machine id
     */
    public String getMachineId() {
        return machineId;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#getDetails()
     */
    public PlayerDetails getDetails() {
        return details;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.game.entity.Entity#getRoomUser()
     */
    public RoomUser getRoomUser() {
        return roomUser;
    }

    /**
     * Gets the messenger.
     *
     * @return the messenger
     */
    public Messenger getMessenger() {
        return messenger;
    }

    /**
     * Gets the inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the subscription.
     *
     * @return the subscription
     */
    public ClubSubscription getSubscription() {
        return subscription;
    }

    /**
     * Gets the network.
     *
     * @return the network
     */
    public PlayerNetwork getNetwork() {
        return network;
    }

    /**
     * Send message.
     *
     * @param message the message
     */
    public void sendMessage(String message) {
        this.send(new BroadcastMessageAlertComposer(message));
    }
    
    /**
     * Send custom message.
     *
     * @param title the title
     * @param message the message
     * @param image the image
     * @param hotelName the hotel name
     * @param url the url
     */
    public void sendCustomMessage(String title, String message, String image, String hotelName, String url) {
        this.send(new RoomNotificationMessageComposer(title, message, image, hotelName, url));
    }
    
    /**
     * Send notification.
     *
     * @param message the message
     */
    public void sendMOTD(String message) {
        this.send(new MOTDNotificationMessageComposer(message));
    }

    /**
     * Send.
     *
     * @param response the response
     */
    public void send(MessageComposer response) {
        try {
            this.network.send(response);
        } catch (Exception e) {
            Log.getErrorLogger().error("[Player] Could not send message to player {}", this.details.getName());
        }
    }

    /**
     * Send queued packet to the server.
     *
     * @param response the response
     */
    public void sendQueued(MessageComposer response) {
        this.network.send(response);
    }

    /**
     * Send all data written in socket.
     */
    public void flushQueue() {
        this.network.flush();
    }

    /**
     * Gets the diffie hellman session instance.
     *
     * @return the diffie hellman
     */
    public DiffieHellman getDiffieHellman() {
        return diffieHellman;
    }

    /**
     * Gets the per-session message handler
     *
     * @return the message handler
     */
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }


    /**
     * Checks if is logged in.
     *
     * @return true, if is logged in
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the logged in.
     *
     * @param loggedIn the new logged in
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    /**
     * Sets the rc4.
     *
     * @param sharedKey the new rc4
     */
    public void setRC4(byte[] sharedKey) {
        this.rc4 = new RC4(sharedKey);
        this.network.addPipelineStage(this.rc4);
    }

    /**
     * Get player logger instance
     *
     * @return logger
     */
    public Logger getLogger() {
		return logger;
	}

    /**
     * Get RC4 decryption class instance
     *
     * @return rc4
     */
    public RC4 getRc4() {
        return rc4;
    }
}
