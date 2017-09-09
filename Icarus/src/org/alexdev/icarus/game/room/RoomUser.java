package org.alexdev.icarus.game.room;

import java.util.HashMap;
import java.util.LinkedList;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.commands.CommandManager;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.furniture.interactions.Interaction;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.messenger.PlayerMessage;
import org.alexdev.icarus.game.pathfinder.Pathfinder;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.chat.ChatType;
import org.alexdev.icarus.game.room.model.Rotation;
import org.alexdev.icarus.log.DateTime;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.FloodFilterMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.CarryObjectComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.TalkMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.util.GameSettings;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RoomUser {

    private int virtualID;
    private int chatColor;
    private int danceID;
    private int roomRequestedID;

    private Position position;
    private Position goal;
    private Position next;

    private boolean isWalking;
    private boolean needsUpdate;
    private boolean isTeleporting;
    private int teleportRoomID;

    private long chatFloodTimer;
    private int chatCount;
    private int lookResetTime;

    private HashMap<EntityStatus, String> statuses;
    private LinkedList<Position> path;
    private Entity entity;
    private Room room;
    private Item currentItem;
    private int carryTimer;
    private int carryItem;
    private boolean isRolling;
    private boolean isWalkingAllowed;

    public RoomUser(Entity entity) {
        this.dispose();
        this.entity = entity;
    }

    public void stopWalking() {

        this.removeStatus(EntityStatus.MOVE);

        this.next = null;
        this.isWalking = false;

        this.updateCurrentItem();

        if (this.entity.getType() == EntityType.PLAYER) {

            PluginManager.callEvent(PluginEvent.ROOM_STOP_WALKING_EVENT, new LuaValue[] {  
                    CoerceJavaToLua.coerce((Player)this.entity), 
                    CoerceJavaToLua.coerce(this.room)
            });
        }

        this.needsUpdate = true;
    }

    public boolean updateCurrentItem() {

        Item item = this.room.getMapping().getHighestItem(this.position.getX(), this.position.getY());

        boolean no_current_item = false;

        if (item != null) {
            if (item.canWalk()) {
                this.currentItem = item;
                this.triggerCurrentItem();
            } else {
                no_current_item = true;
            }
        } else {
            no_current_item = true;
        }

        if (no_current_item) {
            this.currentItem = null;
            return false;
        }

        return true;
    }

    public void triggerCurrentItem() {

        if (this.currentItem == null) {
            this.removeStatus(EntityStatus.SIT);
            this.removeStatus(EntityStatus.LAY);
        } else {
            Interaction handler = this.currentItem.getDefinition().getInteractionType().getHandler();

            if (handler != null) {
                handler.onStopWalking(this.currentItem, this);
            }
        }

        this.position.setZ(this.room.getMapping().getTile(this.position.getX(), this.position.getY()).getHeight());
        this.needsUpdate = true;
    }

    public boolean containsStatus(EntityStatus status) {
        return this.statuses.containsKey(status);
    }

    public void removeStatus(EntityStatus status) {
        this.statuses.remove(status);
    }

    public void setStatus(EntityStatus status, String value) {

        if (this.containsStatus(status)) {
            this.removeStatus(status);
        }

        this.statuses.put(status, value);
    }
    
    /**
     * Chat without spam checking, only sends to self
     * 
     * @param message - the message to chat to room
     */
    public void chatSelf(ChatType type, String message) {
        if (this.entity.getType() == EntityType.PLAYER) {
            ((Player)entity).send(new TalkMessageComposer(this, type, message, this.chatColor));
        }
    }
    
    public void chat(String message, ChatType type, boolean spamCheck) {

        if (this.entity.getType() != EntityType.PLAYER) {

            MessageComposer composer = null;

            if (this.entity.getType() == EntityType.PET) {
                composer = new TalkMessageComposer(this, type, message, 1);
            }
            
            if (this.entity.getType() == EntityType.BOT) {
                composer = new TalkMessageComposer(this, type, message, 2);
            }
            
            this.room.send(composer);

            return;
        }

        Player player = (Player)this.entity;
        boolean isStaff = player.hasPermission("moderator");

        if (spamCheck) {
            if (DateTime.getTimeSeconds() < this.chatFloodTimer && this.chatCount >= GameSettings.MAX_CHAT_BEFORE_FLOOD) {

                if (!isStaff) {
                    player.send(new FloodFilterMessageComposer(GameSettings.CHAT_FLOOD_WAIT));
                    return;
                }
            }
        }
        
        RoomDao.saveChatlog(player, this.room.getData().getID(), type.name(), message);

        if (CommandManager.hasCommand(player, message)) {
            CommandManager.invokeCommand(player, message);
            return;
        } 

        PlayerMessage playerMessage = new PlayerMessage(this.entity.getDetails().getID(), -1, message);    {

            PluginEvent event = type.getEvent();

            PluginManager.callEvent(event, new LuaValue[] {  
                    CoerceJavaToLua.coerce(player),
                    CoerceJavaToLua.coerce(this.room),
                    CoerceJavaToLua.coerce(playerMessage) 
            });

            message = playerMessage.getMessage();
        }
        
        MessageComposer composer = new TalkMessageComposer(this, type, message, this.chatColor);
        this.room.send(composer);

        for (Player person : this.room.getPlayers()) {

            if (this.entity == person) {
                continue;
            }

            person.getRoomUser().lookTowards(this.entity.getRoomUser().getPosition());
        }

        if (spamCheck) {
            if (!player.hasPermission("moderator")) {

                if (DateTime.getTimeSeconds() > this.chatFloodTimer && this.chatCount >= GameSettings.MAX_CHAT_BEFORE_FLOOD) {
                    this.chatCount = 0;
                } else {
                    this.chatCount = this.chatCount + 1;
                }

                this.chatFloodTimer = (DateTime.getTimeSeconds() + GameSettings.CHAT_FLOOD_SECONDS);

            }
        }
    }

    public void lookTowards(Position look) {

        if (this.isWalking) {
            return;
        }

        int diff = this.getPosition().getRotation() - Rotation.calculate(this.position.getX(), this.position.getY(), look.getX(), look.getY());

        if ((this.getPosition().getRotation() % 2) == 0) {

            if (diff > 0) {
                this.position.setHeadRotation(this.getPosition().getRotation() - 1);
            } else if (diff < 0) {
                this.position.setHeadRotation(this.getPosition().getRotation() + 1);
            } else {
                this.position.setHeadRotation(this.getPosition().getRotation());
            }
        }

        this.lookResetTime = 6;
        this.needsUpdate = true;
    }

    public void warpTo(int x, int y, int rotation) {

        if (this.room.getModel().hasInvalidCoordinates(x, y)) {
            return;
        }

        // remove entity from previous tile
        this.room.getMapping().getTile(this.position.getX(), this.position.getY()).setEntity(null);

        this.position.setX(x);
        this.position.setY(y);
        this.position.setZ(this.room.getMapping().getTileHeight(x, y));
        this.position.setRotation(rotation);

        // set entity to new title
        this.room.getMapping().getTile(x, y).setEntity(entity);

        this.needsUpdate = true;
    }

    public void walkTo(int X, int Y) {

        if (this.room.getModel().hasInvalidCoordinates(X, Y)) {
            return;
        }

        if (this.room.getModel().isBlocked(X, Y)) {
            return;
        }

        if (!this.room.getMapping().isTileWalkable(this.entity, X, Y)) {
            return;
        }

        if (this.position.isMatch(new Position(X, Y))) {
            return;
        }

        if (!this.isWalkingAllowed) {
            return;
        }

        this.goal.setX(X);
        this.goal.setY(Y);

        if (this.entity.getType() == EntityType.PLAYER) {

            PluginManager.callEvent(PluginEvent.ROOM_WALK_REQUEST_EVENT, new LuaValue[] {  
                    CoerceJavaToLua.coerce((Player)this.entity),
                    CoerceJavaToLua.coerce(this.room),
                    CoerceJavaToLua.coerce(this.position), 
                    CoerceJavaToLua.coerce(this.goal) 
            });
        }

        LinkedList<Position> path = Pathfinder.makePath(this.entity);

        if (path == null) {
            return;
        }

        if (path.size() == 0) {
            return;
        }

        this.path = path;
        this.isWalking = true;
    }

    public void carryItem(int vendingID) {

        if (vendingID == -1) {
            return;
        }

        this.carryTimer = 0;
        this.carryItem = vendingID;

        if (vendingID > 0)
            this.carryTimer = 240;
        else
            this.carryTimer = 0;

        this.room.send(new CarryObjectComposer(this.virtualID, vendingID)); 
    }

    public void dispose() {

        if (this.statuses != null) {
            this.statuses.clear();
        } else {
            this.statuses = Maps.newHashMap();
        }

        if (this.path != null) {
            this.path.clear();
        } else {
            this.path = Lists.newLinkedList();
        }

        this.position = null;
        this.goal = null;

        this.position = new Position(0, 0, 0);
        this.goal = new Position(0, 0, 0);

        this.chatColor = 0;
        this.roomRequestedID = -1;
        this.virtualID = -1;
        this.danceID = 0;
        this.lookResetTime = -1;
        this.carryItem = 0;
        this.carryTimer = -1;

        this.needsUpdate = false;
        this.isRolling = false;
        this.isWalking = false;
        this.isWalkingAllowed = true;

    }
    
    public void startDancing(int danceID) {
        
        this.danceID = danceID;
        this.room.send(new DanceMessageComposer(this.virtualID, danceID));
    }
    
    public void stopDancing() {
        this.startDancing(0);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getWalkingGoal() {
        return goal;
    }

    public void setGoal(Position goal) {
        this.goal = goal;
    }

    public Position getNext() {
        return next;
    }

    public void setNext(Position next) {
        this.next = next;
    }

    public void updateStatus() {
        this.room.send(new UserStatusMessageComposer(this.entity));
    }

    public boolean isDancing() {
        return this.danceID != 0;
    }
    public int getVirtualID() {
        return virtualID;
    }

    public void setVirtualID(int virtualID) {
        this.virtualID = virtualID;
    }

    public int getChatColor() {
        return chatColor;
    }

    public void setChatColor(int chatColor) {
        this.chatColor = chatColor;
    }

    public int getDanceID() {
        return danceID;
    }

    public void setDanceID(int danceID) {
        this.danceID = danceID;
    }

    public HashMap<EntityStatus, String> getStatuses() {
        return statuses;
    }

    public LinkedList<Position> getPath() {
        return path;
    }

    public void setPath(LinkedList<Position> path) {

        if (this.path != null) {
            this.path.clear();
        }

        this.path = path;
    }

    public boolean needsUpdate() {
        return needsUpdate;
    }

    public void setNeedUpdate(boolean needsWalkUpdate) {
        this.needsUpdate = needsWalkUpdate;
    }

    public Room getRoom() {
        return room;
    }

    public int getRoomID() {
        return (room == null ? 0 : room.getData().getID());
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean isWalking) {
        this.isWalking = isWalking;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getLookResetTime() {
        return lookResetTime;
    }

    public void setLookResetTime(int lookResetTime) {
        this.lookResetTime = lookResetTime;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public int getCarryTimer() {
        return carryTimer;
    }

    public void setCarryTimer(int carryTimer) {
        this.carryTimer = carryTimer;
    }

    public int getCarryItem() {
        return carryItem;
    }

    public void setCarryItem(int carryItem) {
        this.carryItem = carryItem;
    }

    public boolean isRolling() {
        return isRolling;
    }

    public void setRolling(boolean isRolling) {
        this.isRolling = isRolling;
    }

    public boolean isNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public boolean isTeleporting() {
        return isTeleporting;
    }

    public void setTeleporting(boolean isTeleporting) {
        this.isTeleporting = isTeleporting;
    }

    public int getTeleportRoomID() {
        return teleportRoomID;
    }

    public void setTeleportRoomID(int teleportRoomID) {
        this.teleportRoomID = teleportRoomID;
    }

    public boolean isWalkingAllowed() {
        return isWalkingAllowed;
    }

    public void setWalkingAllowed(boolean isWalkingAllowed) {
        this.isWalkingAllowed = isWalkingAllowed;
    }

    public int getRequestedRoomID() {
        return roomRequestedID;
    }

    public void setRequestedRoomID(int roomRequestedID) {
        this.roomRequestedID = roomRequestedID;
    }
}
