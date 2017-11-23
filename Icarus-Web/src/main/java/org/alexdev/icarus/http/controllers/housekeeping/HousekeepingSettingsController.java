package org.alexdev.icarus.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.dao.site.SiteKey;
import org.alexdev.icarus.http.game.GameSettings;
import org.alexdev.icarus.http.mysql.dao.SiteDao;
import org.alexdev.icarus.http.util.SessionUtil;

import java.util.Map;

public class HousekeepingSettingsController {

    /**
     * Handle the /housekeeping/settings/scheduler URI request
     *
     * @param client the connection
     */
    public static void scheduler(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        Template tpl = client.template("housekeeping/settings_scheduler");

        if (client.post().queries().size() > 0) {
            for (Map.Entry<String, String> kvp : client.post().queries().entrySet()) {
                SiteDao.updateKey(SiteKey.valueOf(kvp.getKey().toUpperCase()), kvp.getValue());
                client.session().set("showAlert", true);
                client.session().set("alertType", "success");
                client.session().set("alertMessage", "The variables have been successfully saved");
                GameSettings.getInstance().reload();
            }
        }

        tpl.set("creditsintervalminutes", GameSettings.CREDITS_INTERVAL_MINUTES);
        tpl.set("creditsintervalamount", GameSettings.CREDITS_INTERVAL_AMOUNT);
        tpl.set("ducketsintervalminutes", GameSettings.DUCKETS_INTERVAL_MINUTES);
        tpl.set("ducketsintervalamount", GameSettings.DUCKETS_INTERVAL_AMOUNT);
        tpl.render();

        client.session().set("showAlert", false);
    }

    /**
     * Handle the /housekeeping/settings/camera URI request
     *
     * @param client the connection
     */
    public static void camera(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        Template tpl = client.template("housekeeping/settings_camera");

        if (client.post().queries().size() > 0) {
            for (Map.Entry<String, String> kvp : client.post().queries().entrySet()) {
                SiteDao.updateKey(SiteKey.valueOf(kvp.getKey().toUpperCase()), kvp.getValue());
                client.session().set("showAlert", true);
                client.session().set("alertType", "success");
                client.session().set("alertMessage", "The variables have been successfully saved");
                GameSettings.getInstance().reload();
            }
        }

        tpl.set("cameraenabled", GameSettings.CAMERA_ENABLED);
        tpl.set("camerafilename", GameSettings.CAMERA_FILENAME);
        tpl.set("camerapath", GameSettings.CAMERA_PATH);
        tpl.render();

        client.session().set("showAlert", false);
    }

    /**
     * Handle the /housekeeping/settings/thumbnail URI request
     *
     * @param client the connection
     */
    public static void thumbnail(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        Template tpl = client.template("housekeeping/settings_thumbnail");

        if (client.post().queries().size() > 0) {
            for (Map.Entry<String, String> kvp : client.post().queries().entrySet()) {
                SiteDao.updateKey(SiteKey.valueOf(kvp.getKey().toUpperCase()), kvp.getValue());
                client.session().set("showAlert", true);
                client.session().set("alertType", "success");
                client.session().set("alertMessage", "The variables have been successfully saved");
                GameSettings.getInstance().reload();
            }
        }

        tpl.set("thumbnailenabled", GameSettings.THUMBNAIL_ENABLED);
        tpl.set("thumbnailfilename", GameSettings.THUMBNAIL_FILENAME);
        tpl.set("thumbnailpath", GameSettings.THUMBNAIL_PATH);
        tpl.set("thumbnailurl", GameSettings.THUMBNAIL_URL);
        tpl.render();

        client.session().set("showAlert", false);
    }
}
