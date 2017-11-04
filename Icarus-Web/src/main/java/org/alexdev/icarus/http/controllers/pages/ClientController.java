package org.alexdev.icarus.http.controllers.pages;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;
import org.alexdev.icarus.http.template.binders.TemplateClientBinder;
import org.alexdev.icarus.http.util.SessionUtil;
import org.apache.commons.lang3.RandomUtils;

public class ClientController {

    /**
     * Handle the /hotel URI request
     *
     * @param client the connection
     */
    public static void hotel(WebConnection client) {

        // If they are logged in, send them to the /me page
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN)) {
            client.redirect("/");
            return;
        }

        Player player = client.session().get(SessionUtil.PLAYER, Player.class);
        PlayerDao.updateTicket(player.getId(), player);

        Template tpl = null;

        if (player.getName().isEmpty()) {
            tpl = client.template("client_new");
            tpl.set("newname", "character" + RandomUtils.nextInt(0, 10000));
        } else {
            tpl = client.template("client");
        }

        tpl.set("client", new TemplateClientBinder());
        tpl.set("ticket", player.getSsoTicket());
        tpl.render();
    }
}