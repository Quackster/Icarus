package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class InteractItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        int itemId = reader.readInt();

        Item item = player.getRoom().getItem(itemId);

        if (item == null) {
            return;
        }

        if (item.getDefinition().getInteractionType().getHandler() != null) {
            item.getDefinition().getInteractionType().getHandler().onUseItem(item, player.getRoomUser());
        }

        if (item.getType() == ItemType.FLOOR) {
            PluginManager.callEvent(PluginEvent.FLOOR_ITEM_INTERACT_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(item) });
        } 

        if (item.getType() == ItemType.WALL) {
            PluginManager.callEvent(PluginEvent.WALL_ITEM_INTERACT_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(item) });
        }

        if (player.getMetadata().getAsBool("debugfurniture")) {
            StringBuilder builder = new StringBuilder();
            builder.append("<b>Item settings</b>\n");
            builder.append("item_id: " + item.getId() + "\n");
            builder.append("owner_id: " + item.getOwnerId() + "\n");
            builder.append("height: " + item.getPosition().getZ() + "\n");
            builder.append("\r");
            builder.append("<b>Definition settings</b>\n");
            builder.append("furniture_id: " + item.getDefinition().getId() + "\n");
            builder.append("interaction_type: " + item.getDefinition().getInteractionType().name() + "\n");
            builder.append("allow_trade: " + item.getDefinition().allowTrade() + "\n");
            builder.append("allow_stack: " + item.getDefinition().allowStack() + "\n");
            builder.append("allow_sit: " + item.getDefinition().allowSit() + "\n");
            player.sendMessage(builder.toString());
        }
    }
}
