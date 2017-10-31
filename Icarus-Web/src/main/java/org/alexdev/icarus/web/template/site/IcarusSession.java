package org.alexdev.icarus.web.template.site;

import org.alexdev.duckhttpd.server.session.WebConnection;

public class IcarusSession {

    private boolean loggedIn;
    private boolean housekeeping;
    private boolean showAlert;

    private String alertType;
    private String alertMessage;

    public IcarusSession(WebConnection webConnection) {
        this.loggedIn = webConnection.session().getBoolean("authenticated");
        this.housekeeping = webConnection.session().getBoolean("housekeeping");
        this.showAlert = webConnection.session().getBoolean("showAlert");
        this.alertType = webConnection.session().getString("alertType");
        this.alertMessage = webConnection.session().getString("alertMessage");
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isHousekeeping() {
        return housekeeping;
    }

    public void setHousekeeping(boolean housekeeping) {
        this.housekeeping = housekeeping;
    }

    public boolean isShowAlert() {
        return showAlert;
    }

    public void setShowAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
}
