package org.alexdev.icarus.messages;

import java.util.HashMap;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.incoming.catalogue.CatalogueMessageEvent;
import org.alexdev.icarus.messages.incoming.catalogue.CataloguePageMessageEvent;
import org.alexdev.icarus.messages.incoming.catalogue.PurchaseMessageEvent;
import org.alexdev.icarus.messages.incoming.handshake.*;
import org.alexdev.icarus.messages.incoming.messenger.*;
import org.alexdev.icarus.messages.incoming.misc.*;
import org.alexdev.icarus.messages.incoming.navigator.*;
import org.alexdev.icarus.messages.incoming.room.*;
import org.alexdev.icarus.messages.incoming.room.items.InteractItemMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.InventoryMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.MoveItemMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.PickupItemMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.PlaceItemMessageEvent;
import org.alexdev.icarus.messages.incoming.room.user.*;
import org.alexdev.icarus.messages.incoming.user.*;
import org.alexdev.icarus.messages.outgoing.room.items.ApplyDecorationMessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.common.collect.Maps;

public class MessageHandler {

    //private List<IncomingMessageParser> parsers;
    private HashMap<Integer, MessageEvent> messages;

    public MessageHandler() {
        //this.parsers = Lists.newArrayList();
        this.messages = Maps.newHashMap();
        this.register();
    }
    
    public void register() {
        
        this.messages.clear();
        
        this.registerHandshakePackets();
        this.registerUserPackets();
        this.registerMiscPackets();
        this.registerMessenger();
        this.registerNavigatorPackets();
        this.registerRoomPackets();
        this.registerCataloguePackets();
        this.registerItemPackets();
    }

    private void registerHandshakePackets() {
        this.messages.put(Incoming.VersionCheckMessageEvent, new VersionCheckMessageEvent());
        this.messages.put(Incoming.UniqueIDMessageEvent, new UniqueIDMessageEvent());
        this.messages.put(Incoming.AuthenticateMessageEvent, new AuthenticateMessageEvent());
    }
    
    private void registerUserPackets() {
        this.messages.put(Incoming.InfoRetrieveMessageEvent, new InfoRetrieveMessageEvent());
        this.messages.put(Incoming.CurrencyBalanceMessageEvent, new CurrencyBalanceMessageEvent());
        this.messages.put(Incoming.ChangeAppearanceMessageEvent, new ChangeAppearanceMessageEvent());
    }
    
    private void registerMessenger() {
        this.messages.put(Incoming.MessengerInitMessageEvent, new MessengerInitMessageEvent());
        this.messages.put(Incoming.MessengerSearchMessageEvent, new MessengerSearchMessageEvent());
        this.messages.put(Incoming.MessengerRequestMessageEvent, new MessengerRequestMessageEvent());
        this.messages.put(Incoming.MessengerAcceptMessageEvent, new MessengerAcceptMessageEvent());
        this.messages.put(Incoming.MessengerDeclineMessageEvent, new MessengerDeclineMessageEvent());
        this.messages.put(Incoming.MessengerDeleteFriendMessageEvent, new MessengerDeleteFriendMessageEvent());
        this.messages.put(Incoming.MessengerTalkMessageEvent, new MessengerTalkMessageEvent());
        this.messages.put(Incoming.MessengerUpdateMessageEvent, new MessengerUpdateMessageEvent());
        this.messages.put(Incoming.FollowFriendMessageEvent, new FollowFriendMessageEvent());
    }
    
    private void registerNavigatorPackets() {
        this.messages.put(Incoming.NewNavigatorMessageEvent, new NewNavigatorMessageEvent());
        this.messages.put(Incoming.SearchNewNavigatorEvent, new SearchNewNavigatorEvent());
        this.messages.put(Incoming.CreateRoomMessageEvent, new CreateRoomMessageEvent());
        this.messages.put(Incoming.CanCreateRoomMessageEvent, new CanCreateRoomMessageEvent());
    }

    private void registerMiscPackets() {
        this.messages.put(Incoming.EventLogMessageEvent, new EventLogMessageEvent());
        this.messages.put(Incoming.LatencyTestMessageEvent, new LatencyTestMessageEvent());
    }
    
    private void registerRoomPackets() {
        this.messages.put(Incoming.RoomInfoMessageEvent, new RoomInfoMessageEvent());
        this.messages.put(Incoming.EnterRoomMessageEvent, new EnterRoomMessageEvent());
        this.messages.put(Incoming.HeightMapMessageEvent, new HeightmapMessageEvent());
        this.messages.put(Incoming.UserWalkMessageEvent, new UserWalkMessageEvent());
        this.messages.put(Incoming.LeaveRoomMessageEvent, new LeaveRoomMessageEvent());
        this.messages.put(Incoming.ChatMessageEvent, new ChatMessageEvent());
        this.messages.put(Incoming.ShoutMessageEvent, new ShoutMessageEvent());
        this.messages.put(Incoming.DanceMessageEvent, new DanceMessageEvent());
        this.messages.put(Incoming.StartTypingMessageEvent, new TypingStatusMessageEvent());
        this.messages.put(Incoming.StopTypingMessageEvent, new TypingStatusMessageEvent());
        this.messages.put(Incoming.AnswerDoorbellMessageEvent, new DoorbellAnswerMessageEvent());
        this.messages.put(Incoming.EnterDoorbellMessageEvent, new DoorbellEnterMessageEvent());
        this.messages.put(Incoming.RoomEditInfoMessageEvent, new RoomSettingsDataMessageEvent());
        this.messages.put(Incoming.SaveRoomMessageEvent, new SaveRoomMessageEvent());
        this.messages.put(Incoming.DeleteRoomMessageEvent, new DeleteRoomMessageEvent());
    }
    
    private void registerCataloguePackets() {
        this.messages.put(Incoming.CatalogueTabMessageEvent, new CatalogueMessageEvent());
        this.messages.put(Incoming.CataloguePageMessageEvent, new CataloguePageMessageEvent());
        this.messages.put(Incoming.PurchaseObjectMessageEvent, new PurchaseMessageEvent());
    }
    

    private void registerItemPackets() {
        this.messages.put(Incoming.InventoryMessageEvent, new InventoryMessageEvent());
        this.messages.put(Incoming.PlaceItemMessageEvent, new PlaceItemMessageEvent());
        this.messages.put(Incoming.MoveItemMessageEvent, new MoveItemMessageEvent());
        this.messages.put(Incoming.PickupItemMessageEvent, new PickupItemMessageEvent());
        this.messages.put(Incoming.ApplyDecorationMessageEvent, new ApplyDecorationMessageEvent());
        this.messages.put(Incoming.MoveWallItemMessageEvent, new MoveItemMessageEvent());
        this.messages.put(Incoming.InteractFloorItemMessageEvent, new InteractItemMessageEvent());
        this.messages.put(Incoming.InteractWallItemMessageEvent, new InteractItemMessageEvent());
        this.messages.put(Incoming.DropItemMessageEvent, new DropItemMessageEvent());
    }


    public void handleRequest(Player player, ClientMessage message) {
        if (messages.containsKey(message.getMessageId())) {
            messages.get(message.getMessageId()).handle(player, message);
        }
    }

    public HashMap<Integer, MessageEvent> getMessages() {
        return messages;
    }

}

/*package org.alexdev.icarus.messages;

import java.util.HashMap;
import java.util.List;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.incoming.catalogue.CatalogueMessageEvent;
import org.alexdev.icarus.messages.incoming.catalogue.CataloguePageMessageEvent;
import org.alexdev.icarus.messages.incoming.catalogue.PurchaseMessageEvent;
import org.alexdev.icarus.messages.incoming.handshake.*;
import org.alexdev.icarus.messages.incoming.messenger.*;
import org.alexdev.icarus.messages.incoming.misc.*;
import org.alexdev.icarus.messages.incoming.navigator.*;
import org.alexdev.icarus.messages.incoming.room.*;
import org.alexdev.icarus.messages.incoming.room.items.InventoryMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.PlaceItemMessageEvent;
import org.alexdev.icarus.messages.incoming.room.user.*;
import org.alexdev.icarus.messages.incoming.user.*;
import org.alexdev.icarus.messages.outgoing.room.items.ApplyDecorationMessageEvent;
import org.alexdev.icarus.messages.parsers.IncomingMessageParser;
import org.alexdev.icarus.messages.parsers.handshake.SSOTicketMessageParser;
import org.alexdev.icarus.messages.parsers.handshake.UniqueIDMessageParser;
import org.alexdev.icarus.messages.parsers.handshake.VersionCheckParser;
import org.alexdev.icarus.server.messages.AbstractReader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MessageHandler {

    private HashMap<Short, IncomingMessageParser> parsers;
    private List<MessageEvent> messages;

    public MessageHandler() {
        this.parsers = Maps.newHashMap();
        this.messages = Lists.newArrayList();

        this.registerParsers();
        this.registerMessages();
    }

    private void registerParsers() {
        this.parsers.put(Incoming.UniqueIDMessageEvent, new UniqueIDMessageParser());
        this.parsers.put(Incoming.VersionCheckMessageEvent, new VersionCheckParser());
        this.parsers.put(Incoming.SSOTicketMessageEvent, new SSOTicketMessageParser());
    }
    

    private void registerMessages() {
        
        // handshake
        this.messages.add(new VersionCheckMessageEvent());
        this.messages.add(new UniqueIDMessageEvent());
        this.messages.add(new SSOTicketMessageEvent());

        // registerUserPackets() {
        this.messages.add(new InfoRetrieveMessageEvent());
        this.messages.add(new GetCurrencyBalanceMessageEvent());
    
        // registerMessenger() {
        this.messages.add(new MessengerMessageEvent());
        this.messages.add(new MessengerSearchMessageEvent());
        this.messages.add(new MessengerRequestMessageEvent());
        this.messages.add(new MessengerAcceptMessageEvent());
        this.messages.add(new MessengerDeclineMessageEvent());
        this.messages.add(new MessengerDeleteFriendMessageEvent());
        this.messages.add(new MessengerTalkMessageEvent());
        this.messages.add(new MessengerUpdateMessageEvent());
        this.messages.add(new FollowFriendMessageEvent());
    
        // registerNavigatorPackets() {
        this.messages.add(new NewNavigatorMessageEvent());
        this.messages.add(new SearchNewNavigatorEvent());
        this.messages.add(new CreateRoomMessageEvent());
        this.messages.add(new CanCreateRoomMessageEvent());

        // private void registerMiscPackets() {
        this.messages.add(new EventLogMessageEvent());
        this.messages.add(new LatencyTestMessageEvent());
    
        // private void registerRoomPackets() {
        this.messages.add(new RoomInfoMessageEvent());
        this.messages.add(new GetRoomRightsListMessageEvent());
        this.messages.add(new EnterRoomMessageEvent());
        this.messages.add(new HeightmapMessageEvent());
        this.messages.add(new UserWalkMessageEvent());
        this.messages.add(new LeaveRoomMessageEvent());
        this.messages.add(new ChatMessageEvent());
        this.messages.add(new ShoutMessageEvent());
        this.messages.add(new DanceMessageEvent());
        this.messages.add(new TypingStatusMessageEvent());
        this.messages.add(new TypingStatusMessageEvent());
        this.messages.add(new RoomThumbnailMessageEvent());
        this.messages.add(new DoorbellAnswerMessageEvent());
        this.messages.add(new DoorbellEnterMessageEvent());
        this.messages.add(new RoomSettingsDataMessageEvent());
        this.messages.add(new SaveRoomMessageEvent());
        this.messages.add(new DeleteRoomMessageEvent());
    
        // private void registerCataloguePackets() {
        this.messages.add(new CatalogueMessageEvent());
        this.messages.add(new CataloguePageMessageEvent());
        this.messages.add(new PurchaseMessageEvent());
    
        // private void registerItemPackets() {
        this.messages.add(new InventoryMessageEvent());
        this.messages.add(new PlaceItemMessageEvent());
        this.messages.add(new ApplyDecorationMessageEvent());
    }

    public void handleRequest(Player player, AbstractReader message) {
        if (messages.containsKey(message.getMessageId())) {
            messages.get(message.getMessageId()).handle(player, message);
        }
    }

}
*/
