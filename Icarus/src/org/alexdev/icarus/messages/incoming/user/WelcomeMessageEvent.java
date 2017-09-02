package org.alexdev.icarus.messages.incoming.user;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class WelcomeMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, ClientMessage reader) {

		Globals globals = JsePlatform.standardGlobals();
		globals.set("session", CoerceJavaToLua.coerce(player));

		StringBuilder welcomeMessage = new StringBuilder();
		welcomeMessage.append("Welcome to Icarus Hotel\n\n");
		welcomeMessage.append("This server is currently in alpha stage, some features may still be added, removed, or changed!");

		//LuaValue chunk = globals.load("session:sendMessage([[" + welcomeMessage.toString() + "]])");
		LuaValue chunk = globals.load("print(session:getDetails():getUsername())");
		chunk.call();

	}

}
