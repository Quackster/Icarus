package org.alexdev.icarus.http.controllers;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;
import org.alexdev.icarus.http.util.SessionUtil;

public class RootController {

    /**
     * Handle root call, this is called before any other route is
     */
    public static void root(WebConnection client) {

        // If the user is logged, create their player instance
        if (client.session().getBoolean(SessionUtil.LOGGED_IN) || client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {

            // Creates a player instance and set it
            Player player = PlayerDao.get(client.session().getInt(SessionUtil.USER_ID));
            client.session().set(SessionUtil.PLAYER, player);
        }
    }
}
