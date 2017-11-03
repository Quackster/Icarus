package org.alexdev.icarus.http.controllers;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;

public class RootController {

    /**
     * Handle root call, this is called before any other route is
     */
    public static void root(WebConnection client) {

        // If the user isn't logged, send them back to the index
        if (client.session().getBoolean("authenticated")) {

            // Creates a player instance and set it
            Player player = PlayerDao.get(client.session().getInt("userId"));
            client.session().set("player", player);
        }
    }
}
