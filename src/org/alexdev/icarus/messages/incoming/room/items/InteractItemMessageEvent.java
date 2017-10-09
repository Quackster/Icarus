package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class InteractItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        int itemId = reader.readInt();

        Item item = player.getRoom().getItemManager().getItem(itemId);

        if (item == null) {
            return;
        }
        
        if (item.getDefinition().getInteractionType().get() != null) {
            item.getDefinition().getInteractionType().get().onUseItem(item, player.getRoomUser());
        }

        if (item.getDefinition().getType() == ItemType.FLOOR) {
            PluginManager.callEvent(PluginEvent.FLOOR_ITEM_INTERACT_EVENT, 
                    new LuaValue[] { 
                            CoerceJavaToLua.coerce(player), 
                            CoerceJavaToLua.coerce(item) 
                    });
            
        } else if (item.getDefinition().getType() == ItemType.WALL) {
            PluginManager.callEvent(PluginEvent.WALL_ITEM_INTERACT_EVENT, 
                    new LuaValue[] { 
                            CoerceJavaToLua.coerce(player), 
                            CoerceJavaToLua.coerce(item) 
                    });
            
        }

        if (player.getMetadata().getBoolean("debugfurniture")) {
            StringBuilder builder = new StringBuilder();
            builder.append("<b>Item settings</b>\n");
            builder.append("item_id: " + item.getId() + "\n");
            builder.append("exta_data: " + item.getExtraData() + "\n");
            builder.append("owner_id: " + item.getOwnerId() + "\n");
            builder.append("height: " + item.getPosition().getZ() + "\n");
            builder.append("\r");
            builder.append("<b>Definition settings</b>\n");
            builder.append("furniture_id: " + item.getDefinition().getId() + "\n");
            builder.append("sprite_id: " + item.getDefinition().getSpriteId() + "\n");
            builder.append("height: " + item.getDefinition().getHeight() + "\n");
            builder.append("stack_height: " + item.getDefinition().getStackHeight() + "\n");
            builder.append("interaction_modes: " + item.getDefinition().getInteractionModes() + "\n");
            builder.append("interaction_type: " + item.getDefinition().getInteractionType().name() + "\n");
            builder.append("allow_trade: " + item.getDefinition().allowTrade() + "\n");
            builder.append("allow_stack: " + item.getDefinition().allowStack() + "\n");
            builder.append("allow_sit: " + item.getDefinition().allowSit() + "\n");
            player.sendMessage(builder.toString());
        }
    }
}
