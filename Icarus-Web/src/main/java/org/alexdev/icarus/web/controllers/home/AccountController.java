package org.alexdev.icarus.web.controllers.home;

import ch.compile.recaptcha.ReCaptchaVerify;
import ch.compile.recaptcha.SiteVerifyResponse;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.icarus.web.mysql.dao.PlayerDao;
import org.alexdev.icarus.web.util.config.Configuration;

import java.io.IOException;

public class AccountController {

    /**
     * Handle the /account/login URI request
     * @param client the connection
     */
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

    /**
     * Handle the /logout URI request
     * @param client the connection
     */
    public static void logout(WebConnection client) {
        client.session().set("showAlert", true);
        client.session().set("alertType", "success");
        client.session().set("alertMessage", "Successfully logged out!");
        client.session().set("authenticated", false);
        client.redirect("/");
    }

    /**
     * Handle the /account/register URI request
     * @param client the connection
     */
    public static void register(WebConnection client) throws IOException {

        String[] fieldCheck = new String[] { "regemail", "regpassword", "regconfirmpassword", "g-recaptcha-response" };

        for (String field : fieldCheck) {

            if (client.post().contains(field) &&
                    client.post().get(field).length() > 0) {
                continue;
            }

            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "You need to answer all register fields correctly, including the captcha");
            client.redirect("/register");
            return;
        }

        ReCaptchaVerify reCaptchaVerify = new ReCaptchaVerify(Configuration.PRIVATE_RECAPTCHA_KEY);
        SiteVerifyResponse siteVerifyResponse = reCaptchaVerify.verify(client.post().get("g-recaptcha-response"), client.getIpAddress());

        if (!siteVerifyResponse.isSuccess()) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "warning");
            client.session().set("alertMessage", "The captcha is incorrect");
            client.redirect("/register");
            return;
        }
    }
}
