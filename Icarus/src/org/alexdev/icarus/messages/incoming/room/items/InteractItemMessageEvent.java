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

		PluginEvent interact = null;

		if (item.getType() == ItemType.WALL) {
			interact = PluginEvent.WALL_ITEM_INTERACT_EVENT;
		}

		if (item.getType() == ItemType.FLOOR) {
			interact = PluginEvent.FLOOR_ITEM_INTERACT_EVENT;
		}

		if (interact != null) {
			PluginManager.callEvent(interact, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(item) });
		}
	}
}
