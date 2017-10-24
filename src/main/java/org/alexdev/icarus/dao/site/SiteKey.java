package org.alexdev.icarus.dao.site;

public enum SiteKey {
    USERS_ONLINE("users.online");

    private final String key;

    SiteKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
