package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.web.game.player.Player;
import org.alexdev.icarus.web.mysql.dao.PlayerDao;
import org.alexdev.icarus.web.template.binders.TemplateClientBinder;

public class ClientController {

    /**
     * Handle the /hotel URI request
     * @param client the connection
     */
    public static void hotel(WebConnection client) {

        // If they are logged in, send them to the /me page
       if (!client.session().getBoolean("authenticated")) {
           client.redirect("/");
           return;
        }

        Template tpl = client.template("client_new");
        tpl.set("client", new TemplateClientBinder());
        tpl.render();
    }
}
