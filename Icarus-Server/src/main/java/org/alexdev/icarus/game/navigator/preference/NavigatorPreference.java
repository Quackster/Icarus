package org.alexdev.icarus.game.navigator.preference;

import java.util.HashMap;
import java.util.Map;

public class NavigatorPreference {
    private Map<NavigatorPreferenceType, Map<String, Boolean>> preferences;

    public NavigatorPreference() {
        this.preferences = new HashMap<>();

        for (NavigatorPreferenceType preferenceType : NavigatorPreferenceType.values()) {
            this.preferences.put(preferenceType, new HashMap<>());
        }
    }

    /**
     * Add the preference type to the hashmap
     *
     * @param preferenceType the type of preference setting
     * @param tabName the navigator tab name
     * @param flag the flag to set
     */
    public void addPreference(NavigatorPreferenceType preferenceType, String tabName, boolean flag) {
        if (this.preferences.containsKey(preferenceType)) {
            this.preferences.get(preferenceType).put(tabName, flag);
        }
    }

    /**
     * Get flag setting
     *
     * @param preferenceType the type of preference
     * @param tabName the tab name
     * @return the flag setting
     */
    public boolean getFlag(NavigatorPreferenceType preferenceType, String tabName) {
        if (this.preferences.containsKey(preferenceType)) {
            return this.preferences.get(preferenceType).get(tabName);
        }

        return false;
    }
}


