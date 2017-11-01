package org.alexdev.icarus.web.controllers.home;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.web.mysql.dao.PlayerDao;

public class AccountController {

    public static void login(WebConnection client) {

        String[] fieldCheck = new String[] { "email", "password" };

        for (String field : fieldCheck) {

            if (client.post().contains(field) &&
                client.post().get(field).length() > 0) {
                continue;
            }

            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "You need to enter both your email and password");
            client.redirect("/");
            return;
        }

        if (!PlayerDao.exists(client.post().get("email"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "That account does not exist!");
            client.redirect("/");
            return;
        }

        if (!PlayerDao.valid(client.post().get("email"), client.post().get("password"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "You have entered an invalid password");
            client.redirect("/");
            return;
        }

        client.session().set("authenticated", true);
        client.redirect("/me");
    }

    public static void logout(WebConnection client) {
        /*String header = "Successfully logged out";
        String message;
        FullHttpResponse response = Settings.getInstance().getResponses().getErrorResponse(webConnection, header, message);*/

        client.session().set("showAlert", true);
        client.session().set("alertType", "warning");
        client.session().set("alertMessage", "Successfully logged out!");
        client.session().set("authenticated", false);
        client.redirect("/");
    }

    public static void me(WebConnection client) throws Exception {

        if (!client.session().getBoolean("authenticated")) {
            client.session().set("authenticated", false);
            client.redirect("/home");
            return;
        }

        Template tpl = client.template("me");
        tpl.render();
    }


}
