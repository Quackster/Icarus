package org.alexdev.icarus.http.template.binders;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.icarus.http.game.player.Player;

public class TemplateSessionBinder {

    private boolean loggedIn;
    private boolean housekeeping;
    private boolean showAlert;

    private String alertType;
    private String alertMessage;

    private String figure = "lg-270-90.sh-290-64.hd-180-1.ch-3015-1341.hr-828-45";

    public TemplateSessionBinder(WebConnection webConnection) {

        Player player = webConnection.session().get("player", Player.class);

        if (player != null) {
            this.housekeeping = player.hasHouskeeping();
        }

        this.loggedIn = webConnection.session().getBoolean("authenticated");
        this.showAlert = webConnection.session().getBoolean("showAlert");
        this.alertType = webConnection.session().getString("alertType");
        this.alertMessage = webConnection.session().getString("alertMessage");
    }
}
