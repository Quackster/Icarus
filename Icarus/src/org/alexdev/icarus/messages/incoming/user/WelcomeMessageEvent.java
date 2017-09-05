package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class WelcomeMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        /*Globals globals = JsePlatform.standardGlobals();
        globals.set("session", CoerceJavaToLua.coerce(player));

        StringBuilder welcomeMessage = new StringBuilder();
        welcomeMessage.append("Welcome to Icarus Hotel\n\n");
        welcomeMessage.append("This server is currently in alpha stage, some features may still be added, removed, or changed!");

        LuaValue chunk = globals.load("session:sendMessage([[" + welcomeMessage.toString() + "]])");
        chunk.call();*/
        
        PluginManager.callEvent(PluginEvent.PLAYER_LOGIN_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player) });

    }

}
