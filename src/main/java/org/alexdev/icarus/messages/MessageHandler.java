package org.alexdev.icarus.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.incoming.camera.*;
import org.alexdev.icarus.messages.incoming.catalogue.*;
import org.alexdev.icarus.messages.incoming.groups.*;
import org.alexdev.icarus.messages.incoming.groups.edit.*;
import org.alexdev.icarus.messages.incoming.groups.members.*;
import org.alexdev.icarus.messages.incoming.handshake.*;
import org.alexdev.icarus.messages.incoming.items.*;
import org.alexdev.icarus.messages.incoming.messenger.*;
import org.alexdev.icarus.messages.incoming.misc.*;
import org.alexdev.icarus.messages.incoming.navigator.*;
import org.alexdev.icarus.messages.incoming.pets.*;
import org.alexdev.icarus.messages.incoming.room.*;
import org.alexdev.icarus.messages.incoming.room.floorplan.*;
import org.alexdev.icarus.messages.incoming.room.items.*;
import org.alexdev.icarus.messages.incoming.room.items.interactions.MoodlightInteractMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.interactions.SaveBrandingMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.interactions.SaveMannequinMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.interactions.SaveMoodlightPresetMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.interactions.SaveTonerMessageEvent;
import org.alexdev.icarus.messages.incoming.room.items.interactions.ToggleMoodlightMessageEvent;
import org.alexdev.icarus.messages.incoming.room.settings.*;
import org.alexdev.icarus.messages.incoming.room.user.*;
import org.alexdev.icarus.messages.incoming.trading.StartTradingMessageEvent;
import org.alexdev.icarus.messages.incoming.user.*;
import org.alexdev.icarus.messages.incoming.user.club.HabboClubCenterMessageEvent;
import org.alexdev.icarus.messages.incoming.user.club.SubscriptionMessageEvent;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class MessageHandler {

    private static HashMap<Short, List<MessageEvent>> messages;
    private static HashMap<Short, String> messageLookup;

    public static void load() throws Exception {
        messages = new HashMap<>();
        messageLookup = new HashMap<>();
        register();
    }

    public static void register() {
        messages.clear();
        registerHandshakePackets();
        registerUserPackets();
        registerMiscPackets();
        registerMessenger();
        registerNavigatorPackets();
        registerRoomPackets();
        registerCataloguePackets();
        registerItemPackets();
        registerPetPackets();
        registerRoomSettingPackets();
        registerRoomFloorplanPackets();
        registerGroupPackets();
        registerTradePackets();
        registerCameraPackets();
        registerItemInteractionPackets();
    }

    /**
     * Register handshake packets.
     */
    private static void registerHandshakePackets() {
        registerEvent(Incoming.VersionCheckMessageEvent, new VersionCheckMessageEvent());
        registerEvent(Incoming.InitCryptoMessageEvent, new InitCryptoMessageEvent());
        registerEvent(Incoming.GenerateSecretKeyMessageEvent, new GenerateSecretKeyMessageEvent());
        registerEvent(Incoming.UniqueIDMessageEvent, new UniqueIDMessageEvent());
        registerEvent(Incoming.AuthenticateMessageEvent, new AuthenticateMessageEvent());
    }

    /**
     * Register user packets.
     */
    private static void registerUserPackets() {
        registerEvent(Incoming.InfoRetrieveMessageEvent, new InfoRetrieveMessageEvent());
        registerEvent(Incoming.CurrencyBalanceMessageEvent, new CurrencyBalanceMessageEvent());
        registerEvent(Incoming.ChangeAppearanceMessageEvent, new ChangeAppearanceMessageEvent());
        registerEvent(Incoming.SubscriptionMessageEvent, new SubscriptionMessageEvent());
        registerEvent(Incoming.NavigatorPromoteRoomCategories, new WelcomeMessageEvent());
        registerEvent(Incoming.HabboClubCenterMessageEvent, new HabboClubCenterMessageEvent());
    }

    /**
     * Register messenger.
     */
    private static void registerMessenger() {
        registerEvent(Incoming.MessengerInitMessageEvent, new MessengerInitMessageEvent());
        registerEvent(Incoming.MessengerSearchMessageEvent, new MessengerSearchMessageEvent());
        registerEvent(Incoming.MessengerRequestMessageEvent, new MessengerRequestMessageEvent());
        registerEvent(Incoming.MessengerAcceptMessageEvent, new MessengerAcceptMessageEvent());
        registerEvent(Incoming.MessengerDeclineMessageEvent, new MessengerDeclineMessageEvent());
        registerEvent(Incoming.MessengerDeleteFriendMessageEvent, new MessengerDeleteFriendMessageEvent());
        registerEvent(Incoming.MessengerTalkMessageEvent, new MessengerTalkMessageEvent());
        registerEvent(Incoming.MessengerUpdateMessageEvent, new MessengerUpdateMessageEvent());
        registerEvent(Incoming.FollowFriendMessageEvent, new FollowFriendMessageEvent());
    }

    /**
     * Register navigator packets.
     */
    private static void registerNavigatorPackets() {
        registerEvent(Incoming.NewNavigatorMessageEvent, new NewNavigatorMessageEvent());
        registerEvent(Incoming.NavigatorPromoteRoomCategories, new NavigatorPromoteCategoriesMessageEvent());
        registerEvent(Incoming.SearchNewNavigatorEvent, new SearchNewNavigatorEvent());
        registerEvent(Incoming.CreateRoomMessageEvent, new CreateRoomMessageEvent());
        registerEvent(Incoming.CanCreateRoomMessageEvent, new CanCreateRoomMessageEvent());
    }

    /**
     * Register misc packets.
     */
    private static void registerMiscPackets() {
        registerEvent(Incoming.EventLogMessageEvent, new EventLogMessageEvent());
        registerEvent(Incoming.LatencyTestMessageEvent, new LatencyTestMessageEvent());
    }

    /**
     * Register room packets.
     */
    private static void registerRoomPackets() {
        registerEvent(Incoming.EnterRoomMessageEvent, new EnterRoomMessageEvent());
        registerEvent(Incoming.HeightMapMessageEvent, new HeightmapMessageEvent());
        registerEvent(Incoming.UserWalkMessageEvent, new UserWalkMessageEvent());
        registerEvent(Incoming.LeaveRoomMessageEvent, new LeaveRoomMessageEvent());
        registerEvent(Incoming.ChatMessageEvent, new ChatMessageEvent());
        registerEvent(Incoming.ShoutMessageEvent, new ShoutMessageEvent());
        registerEvent(Incoming.DanceMessageEvent, new DanceMessageEvent());
        registerEvent(Incoming.StartTypingMessageEvent, new TypingStatusMessageEvent());
        registerEvent(Incoming.StopTypingMessageEvent, new TypingStatusMessageEvent());
        registerEvent(Incoming.AnswerDoorbellMessageEvent, new DoorbellAnswerMessageEvent());
        registerEvent(Incoming.EnterDoorbellMessageEvent, new DoorbellEnterMessageEvent());
        registerEvent(Incoming.ThumbnailMessageEvent, new ThumbnailMessageEvent());
    }

    /**
     * Register room setting packets.
     */
    private static void registerRoomSettingPackets() {
        registerEvent(Incoming.RoomInfoMessageEvent, new RoomInfoMessageEvent());
        registerEvent(Incoming.SaveRoomMessageEvent, new SaveRoomMessageEvent());
        registerEvent(Incoming.RoomEditInfoMessageEvent, new RoomEditMessageEvent());
        registerEvent(Incoming.RoomRightsMessageEvent, new RoomRightsMessageEvent());
        registerEvent(Incoming.RemoveRightsMessageEvent, new RemoveRightsMessageEvent());
        registerEvent(Incoming.GiveRightsMessageEvent, new GiveRightsMessageEvent());
        registerEvent(Incoming.DeleteRoomMessageEvent, new DeleteRoomMessageEvent());
        registerEvent(Incoming.ClearRoomRightsMessageEvent, new ClearRoomRightsMessageEvent());
    }

    /**
     * Register room floorplan packets.
     */
    private static void registerRoomFloorplanPackets() {
        registerEvent(Incoming.FloorPlanPropertiesMessageEvent, new FloorPlanPropertiesMessageEvent());
        registerEvent(Incoming.SaveFloorPlanMessageEvent, new SaveFloorPlanMessageEvent());
    }

    /**
     * Register catalogue packets.
     */
    private static void registerCataloguePackets() {
        registerEvent(Incoming.CatalogueTabMessageEvent, new CatalogueTabsMessageEvent());
        registerEvent(Incoming.CataloguePageMessageEvent, new CataloguePageMessageEvent());
        registerEvent(Incoming.PurchaseObjectMessageEvent, new PurchaseItemMessageEvent());
        registerEvent(Incoming.PurchasePresentMessageEvent, new PurchasePresentMessageEvent());
        registerEvent(Incoming.GiftingSettingsMessageEvent, new GiftingSettingsMessageEvent());
        registerEvent(Incoming.PromotableRoomsMessageEvent, new PromotableRoomsMessageEvent());
        registerEvent(Incoming.PurchaseRoomPromotionMessageEvent, new PurchaseRoomPromotionMessageEvent());
    }

    /**
     * Register pet packets.
     */
    private static void registerPetPackets() {
        registerEvent(Incoming.PlacePetMessageEvent, new PlacePetMessageEvent());
        registerEvent(Incoming.PetRacesMessageEvent, new PetRacesMessageEvent());
        registerEvent(Incoming.VerifyPetNameMessageEvent, new VerifyPetNameMessageEvent());
        registerEvent(Incoming.PetInfoMessageEvent, new PetInformationMessageEvent());
        registerEvent(Incoming.RemovePetMessageEvent, new RemovePetMessageEvent());
    }

    /**
     * Register item packets.
     */
    private static void registerItemPackets() {
        registerEvent(Incoming.InventoryMessageEvent, new InventoryMessageEvent());
        registerEvent(Incoming.PlaceItemMessageEvent, new PlaceItemMessageEvent());
        registerEvent(Incoming.MoveItemMessageEvent, new MoveItemMessageEvent());
        registerEvent(Incoming.PickupItemMessageEvent, new PickupItemMessageEvent());
        registerEvent(Incoming.ApplyDecorationMessageEvent, new ApplyDecorationMessageEvent());
        registerEvent(Incoming.MoveWallItemMessageEvent, new MoveItemMessageEvent());
        registerEvent(Incoming.InteractFloorItemMessageEvent, new InteractItemMessageEvent());
        registerEvent(Incoming.InteractWallItemMessageEvent, new InteractItemMessageEvent());
        registerEvent(Incoming.DropItemMessageEvent, new DropItemMessageEvent());
        registerEvent(Incoming.PurchaseOfferMessageEvent, new PurchaseOfferMessageEvent());
        registerEvent(Incoming.ApplyEffectMessageEvent, new ApplyEffectMessageEvent());
    }

    private static void registerItemInteractionPackets() {
        registerEvent(Incoming.SaveBrandingMessageEvent, new SaveBrandingMessageEvent());
        registerEvent(Incoming.SaveMannequinMessageEvent, new SaveMannequinMessageEvent());
        registerEvent(Incoming.UseOneWayGateMessageEvent, new InteractItemMessageEvent());
        registerEvent(Incoming.MoodlightInteractMessageEvent, new MoodlightInteractMessageEvent());
        registerEvent(Incoming.ToggleMoodlightMessageEvent, new ToggleMoodlightMessageEvent());
        registerEvent(Incoming.SaveMoodlightPresetMessageEvent, new SaveMoodlightPresetMessageEvent());
        registerEvent(Incoming.SaveTonerMessageEvent, new SaveTonerMessageEvent());
    }

    /**
     * Register group packets.
     */
    private static void registerGroupPackets() {
        registerEvent(Incoming.GroupCatalogueMessageEvent, new GroupCatalogueMessageEvent());
        registerEvent(Incoming.GroupBadgeDialogMessageEvent, new GroupBadgeDialogMessageEvent());
        registerEvent(Incoming.GroupPurchaseMessageEvent, new GroupPurchaseMessageEvent());
        registerEvent(Incoming.GroupInfoMessageEvent, new GroupInfoMessageEvent());
        registerEvent(Incoming.DeleteGroupMessageEvent, new DeleteGroupMessageEvent());
        registerEvent(Incoming.GroupManageDetailsMessageEvent, new GroupManageDetailsMessageEvent());
        registerEvent(Incoming.GroupManageMembersMessageEvent, new GroupManageMembersMessageEvent());
        registerEvent(Incoming.GroupMembershipRequestMessageEvent, new GroupMembershipRequestMessageEvent());
        registerEvent(Incoming.GroupMembershipAcceptMessageEvent, new GroupMembershipAcceptMessageEvent());
        registerEvent(Incoming.GroupMembershipRejectMessageEvent, new GroupMembershipRejectMessageEvent());
        registerEvent(Incoming.GroupRemoveMemberMessageEvent, new GroupRemoveMemberMessageEvent());
        registerEvent(Incoming.GroupGiveAdminMessageEvent, new GroupGiveAdminMessageEvent());
        registerEvent(Incoming.GroupRemoveAdminMessageEvent, new GroupRemoveAdminMessageEvent());
        registerEvent(Incoming.EditGroupTextMessageEvent, new EditGroupTextMessageEvent());
        registerEvent(Incoming.EditGroupColoursMessageEvent, new EditGroupColoursMessageEvent());
        registerEvent(Incoming.EditGroupAccessMessageEvent, new EditGroupAccessMessageEvent());
        registerEvent(Incoming.EditGroupBadgeMessageEvent, new EditGroupBadgeMessageEvent());
    }

    /**
     * Register camera packets.
     */
    private static void registerCameraPackets() {
        registerEvent(Incoming.PhotoPricingMessageEvent, new PhotoPricingMessageEvent());
        registerEvent(Incoming.PreviewPhotoMessageEvent, new PreviewPhotoMessageEvent());
        registerEvent(Incoming.PurchasePhotoMessageEvent, new PurchasePhotoMessageEvent());
        registerEvent(Incoming.DeletePhotoMessageEvent, new DeletePhotoMessageEvent());
    }

    /**
     * Register trade packets.
     */
    private static void registerTradePackets() {
        registerEvent(Incoming.StartTradingMessageEvent, new StartTradingMessageEvent());
    }

    /**
     * Register event.
     *
     * @param header the header
     * @param messageEvent the message event
     */
    private static void registerEvent(Short header, MessageEvent messageEvent) {

        if (!messages.containsKey(header)) {
            messages.put(header, new ArrayList<>());
        }

        if (!messageLookup.containsKey(header)) {
            messageLookup.put(header, messageEvent.getClass().getSimpleName());
        }

        messages.get(header).add(messageEvent);
    }

    /**
     * Handle request.
     *
     * @param player the player
     * @param message the message
     */
    public static void handleRequest(Player player, ClientMessage message) {

            if (Util.getServerConfig().get("Logging", "log.received.packets", Boolean.class)) {
                if (messageLookup.containsKey(message.getMessageId())) {
                    player.getLogger().info("Received ({}): {} / {} ", messageLookup.get(message.getMessageId()), message.getMessageId(), message.getMessageBody());
                } else {
                    player.getLogger().info("Received ({}): {} / {} ", "Unknown", message.getMessageId(), message.getMessageBody());
                }
        }

        invoke(player, message.getMessageId(), message);
    }

    /**
     * Invoke the request.
     *
     * @param player the player
     * @param messageId the message id
     * @param message the message
     */
    public static void invoke(Player player, short messageId, ClientMessage message) {

        if (messages.containsKey(messageId)) {

            for (MessageEvent event : messages.get(messageId)) {
                event.handle(player, message);
            }
        }
    }

    /**
     * Gets the messages.
     *
     * @return the messages
     */
    public static HashMap<Short, List<MessageEvent>> getMessages() {
        return messages;
    }
}
