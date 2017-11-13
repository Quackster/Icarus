package org.alexdev.icarus.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.dao.mysql.site.SiteDao;
import org.alexdev.icarus.dao.site.SiteKey;
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
            }
        }

        tpl.set("creditsintervalminutes", SiteDao.get(SiteKey.CREDITS_INTERVAL_MINUTES));
        tpl.set("creditsintervalamount", SiteDao.get(SiteKey.CREDITS_INTERVAL_AMOUNT));
        tpl.set("ducketsintervalminutes", SiteDao.get(SiteKey.DUCKETS_INTERVAL_MINUTES));
        tpl.set("ducketsintervalamount", SiteDao.get(SiteKey.DUCKETS_INTERVAL_AMOUNT));
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
            }
        }

        tpl.set("cameraenabled", SiteDao.get(SiteKey.CAMERA_ENABLED));
        tpl.set("camerafilename", SiteDao.get(SiteKey.CAMERA_FILENAME));
        tpl.set("camerapath", SiteDao.get(SiteKey.CAMERA_PATH));
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
            }
        }

        tpl.set("thumbnailenabled", SiteDao.get(SiteKey.THUMBNAIL_ENABLED));
        tpl.set("thumbnailfilename", SiteDao.get(SiteKey.THUMBNAIL_FILENAME));
        tpl.set("thumbnailpath", SiteDao.get(SiteKey.THUMBNAIL_PATH));
        tpl.set("thumbnailurl", SiteDao.get(SiteKey.THUMBNAIL_URL));
        tpl.render();

        client.session().set("showAlert", false);
    }
}
