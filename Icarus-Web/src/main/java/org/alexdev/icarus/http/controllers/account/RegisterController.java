package org.alexdev.icarus.http.controllers.account;

import ch.compile.recaptcha.ReCaptchaVerify;
import ch.compile.recaptcha.SiteVerifyResponse;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;
import org.alexdev.icarus.http.util.SessionUtil;
import org.alexdev.icarus.http.util.config.Configuration;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;

public class RegisterController {

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

        if (PlayerDao.emailExists(client.post().get("regemail"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "The email you chose is already in use!");
            client.redirect("/register");
            return;
        }

        if (!client.post().get("regpassword").equals(client.post().get("regconfirmpassword"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "The two passwords do not match");
            client.redirect("/register");
            return;
        }

        if (client.post().get("regpassword").length() < 6) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "Your password needs to be at least 6 or more characters");
            client.redirect("/register");
            return;
        }

        if (!EmailValidator.getInstance().isValid(client.post().get("regemail"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "The email you have entered is not valid");
            client.redirect("/register");
            return;
        }

        int userId = PlayerDao.create(client.post().get("regemail"), client.post().get("regpassword"));

        client.session().set(SessionUtil.LOGGED_IN, true);
        client.session().set(SessionUtil.USER_ID, userId);
        client.redirect("/me");
    }
}
