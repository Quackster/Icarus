package org.alexdev.icarus.game.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.ItemDao;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.ChatOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HasOwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.FloorItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.WallItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.CarryObjectComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveUserMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Room {

	private int privateId = -1;

	private RoomData data;
	private RoomScheduler scheduler;
	private RoomMapping mapping;

	private List<Entity> entities; 
	private Map<Integer, Item> items;


	public Room() {
		this.data = new RoomData(this);
		this.mapping = new RoomMapping(this);

		this.items = Maps.newHashMap();
		this.entities = Lists.newArrayList();
	}

	public void loadRoom(Player player, String pass) {
		this.loadRoom(player, pass, this.getModel().getDoorX(), this.getModel().getDoorY(), this.getModel().getDoorRot());
	}

	public void loadRoom(Player player, String pass, int x, int y, int rotation) {

		if (player.inRoom()) {
			player.getRoom().leaveRoom(player, false);
		}

		boolean isOwner = this.hasRights(player, true);
	
		if (this.data.getUsersNow() >= this.data.getUsersMax()) {
			if (!player.getDetails().hasFuse("user_enter_full_rooms") && player.getDetails().getId() != this.data.getOwnerId()) {
				player.send(new RoomEnterErrorMessageComposer(1));
				return;
			}
		}

		if (player.getRoomUser().isTeleporting()) {
			if (player.getRoomUser().getTeleportRoomId() != this.data.getId()) {
				this.leaveRoom(player, true);
			} else {
				player.getRoomUser().setTeleporting(false);
				player.getRoomUser().setTeleportRoomId(0);
			}
		}
		else {
			
			if (this.data.getState().getStateCode() > 0 && !this.hasRights(player, false)) {
				if (this.data.getState() == RoomState.DOORBELL) {

					if (this.getPlayers().size() > 0) {
						player.send(new GenericDoorbellMessageComposer(1));
						this.send(new GenericDoorbellMessageComposer(player.getDetails().getUsername()), true);
					} else {
						player.send(new GenericNoAnswerDoorbellMessageComposer());
					}

					return;
				}

				if (this.data.getState() == RoomState.PASSWORD) {
					if (!pass.equals(this.data.getPassword())) {
						player.send(new GenericErrorMessageComposer(-100002));
						return;
					}
				}
			}
		}

		RoomUser roomUser = player.getRoomUser();

		roomUser.setRoom(this);
		roomUser.getStatuses().clear();

		player.send(new RoomModelMessageComposer(this.getModel().getName(), this.getData().getId()));
		player.send(new RoomRatingMessageComposer(this.data.getScore()));

		int floorData = Integer.parseInt(this.data.getFloor());
		int wallData = Integer.parseInt(this.data.getWall());

		if (floorData > 0) {
			player.send(new RoomSpacesMessageComposer("floor", this.data.getFloor()));
		}

		if (wallData > 0) {
			player.send(new RoomSpacesMessageComposer("wallpaper", this.data.getWall()));
		}

		player.send(new RoomSpacesMessageComposer("landscape", this.data.getLandscape()));
		player.send(new RoomOwnerRightsComposer(this.data.getId(), isOwner));

		if (roomUser.getRoom().hasRights(player, true)) {
			
			player.send(new RoomRightsLevelMessageComposer(4));
			player.send(new HasOwnerRightsMessageComposer());


		} else if (roomUser.getRoom().hasRights(player, false)) {
			player.send(new RoomRightsLevelMessageComposer(1));

		} else {
			player.send(new RoomRightsLevelMessageComposer(0));
		}

		player.send(new PrepareRoomMessageComposer(this));

		roomUser.setVirtualId(this.getVirtualId());
		roomUser.getPosition().setX(x);
		roomUser.getPosition().setY(y);
		roomUser.getPosition().setZ(this.getModel().getHeight(roomUser.getPosition().getX(), roomUser.getPosition().getY()));
		roomUser.getPosition().setRotation(this.getModel().getDoorRot());

		if (!(this.getPlayers().size() > 0)) {
			this.firstEntry();
		}
	}
	
	public void loadMapData(Player player) {


		player.send(new HeightMapMessageComposer(this, this.getModel().getMapSizeX(), this.getModel().getMapSizeY()));
		player.send(new FloorMapMessageComposer(this));

		this.send(new UserDisplayMessageComposer(player));
		this.send(new UserStatusMessageComposer(player));

		if (!this.getEntities().contains(player)) {
			this.getEntities().add(player);
			this.getData().updateUsersNow();
		}

		player.send(new UserDisplayMessageComposer(this.getEntities()));
		player.send(new UserStatusMessageComposer(this.getEntities()));

		for (Player players : this.getPlayers()) {
			if (players.getRoomUser().isDancing()) {
				player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
			}

			if (players.getRoomUser().getCarryItem() > 0) {
				player.send(new CarryObjectComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getCarryItem())); 
			}
		}

		if (this.hasRights(player, false)) {
			player.getRoomUser().setStatus("flatctrl", " 1", true, -1);
		}        

		player.send(new RoomDataMessageComposer(this, player, true, true));

		player.send(new ChatOptionsMessageComposer(this));
		player.send(new WallOptionsMessageComposer(this.getData().isHideWall(), this.getData().getWallThickness(), this.getData().getFloorThickness()));

		player.send(new FloorItemsMessageComposer(this.getFloorItems()));
		player.send(new WallItemsMessageComposer(this.getWallItems()));

		player.getMessenger().sendStatus(false);

	}

	private void firstEntry() {

		this.items = ItemDao.getRoomItems(this.getData().getId());
		this.mapping.regenerateCollisionMaps();

		this.scheduler = new RoomScheduler(this);
		this.scheduler.scheduleTasks();
	}


	public void leaveRoom(Player player, boolean hotelView) {

		if (hotelView) {;
			player.send(new HotelViewMessageComposer());
		}

		if (this.entities != null) {
			this.entities.remove(player);
			this.data.updateUsersNow();
		}

		if (this.getPlayers().size() > 0) {
			this.send(new RemoveUserMessageComposer(player.getRoomUser().getVirtualId()));
		}

		player.getRoomUser().dispose();
		player.getMessenger().sendStatus(false);

		this.dispose(false);
	}

	public boolean hasRights(Player player, boolean ownerCheckOnly) {

		if (player.hasPermission("room_all_rights")) {
			return true;
		}

		int userID = player.getDetails().getId();

		if (this.data.getOwnerId() == userID) {
			return true;
		} else {
			if (!ownerCheckOnly) {
				return this.data.getRights().contains(Integer.valueOf(userID));
			}
		}

		return false;
	}

	public void dispose(boolean forceDisposal) {

		if (forceDisposal) {

			this.cleanupRoomData();

			RoomManager.getLoadedRooms().remove(this);

		} else {

			if (this.getPlayers().size() > 0) {
				return;
			}

			this.cleanupRoomData();

			if (PlayerManager.getById(this.data.getOwnerId()) == null && this.data.getRoomType() == RoomType.PRIVATE) { 
				RoomManager.getLoadedRooms().remove(this);
			}
		}
	}

	private void cleanupRoomData() {

		if (this.scheduler != null) {
			this.scheduler.cancelTasks();
		}

		if (this.entities != null) {
			this.entities.clear();
		}

		this.privateId = -1;
	}

	public void send(OutgoingMessageComposer response, boolean checkRights) {

		for (Player player : this.getPlayers()) {

			if (checkRights && this.hasRights(player, false)) {
				player.send(response);
			}
		}
	}

	public void send(OutgoingMessageComposer response) {


		for (Player player : this.getPlayers()) {
			player.send(response);
		}
	}

	public List<Player> getPlayers() {

		List<Player> sessions = new ArrayList<Player>();

		for (Entity entity : this.getEntities(EntityType.PLAYER)) {
			Player player = (Player)entity;
			sessions.add(player);
		}

		return sessions;
	}

	public List<Entity> getEntities(EntityType type) {
		List<Entity> e = new ArrayList<Entity>();

		for (Entity entity : this.entities) {
			if (entity.getType() == type) {
				e.add(entity);
			}
		}

		return e;
	}

	public Item[] getFloorItems() {
		List<Item> floorItems = items.values().stream().filter(item -> item.getType() == ItemType.FLOOR).collect(Collectors.toList());
		return floorItems.toArray(new Item[floorItems.size()]);
	}

	public Item[] getWallItems() {
		List<Item> wallItems = items.values().stream().filter(item -> item.getType() == ItemType.WALL).collect(Collectors.toList());
		return wallItems.toArray(new Item[wallItems.size()]);
	}

	public Map<Integer, Item> getItems() {
		return this.items;
	}  

	public List<Item> getItems(InteractionType interactionType) {
		try {
			return items.values().stream().filter(item -> item.getDefinition().getInteractionType() == interactionType).collect(Collectors.toList());
		} catch (Exception e) {
			return Lists.newArrayList();
		}
	}

	public Item getItem(int itemId) {

		if (this.items.containsKey(itemId)) {
			return this.items.get(itemId);
		}

		return ItemDao.getItem(itemId);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public RoomData getData() {
		return data;
	}

	public RoomModel getModel() {
		return RoomDao.getModel(this.data.getModel());
	}

	public RoomMapping getMapping() {
		return mapping;
	}

	public void save() {
		RoomDao.updateRoom(this);
	}
	public int getVirtualId() {
		this.privateId = this.privateId + 1;
		return this.privateId;
	}
}
