package org.alexdev.icarus.web.controllers.home;

import io.netty.handler.codec.http.HttpHeaderNames;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
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
            client.redirect("/home");
            return;
        }

        if (!PlayerDao.exists(client.post().get("email"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "That account does not exist!");
            client.redirect("/home");
            return;
        }

        if (!PlayerDao.valid(client.post().get("email"), client.post().get("password"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "You have entered an invalid password");
            client.redirect("/home");
            return;
        }

        client.redirect("/me");
        //client.cookies().set("authenticated", "1");
    }

    public static void me(WebConnection client) throws Exception {

        if (client.cookies().getString("authenticated", "0").equals("0")) {
            //client.connection().set("authenticated", false);
            //client.redirect("/home");
            //return;
        }

        Template tpl = client.template("me");
        tpl.render();
    }
}
