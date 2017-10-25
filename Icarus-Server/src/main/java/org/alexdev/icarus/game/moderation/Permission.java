package org.alexdev.icarus.game.moderation;

public class Permission {

    private String permission;
    private boolean inheritable;
    private int rank;

    public Permission(String permission, boolean inheritable, int rank) {
        this.permission = permission;
        this.inheritable = inheritable;
        this.rank = rank;
    }

    /**
     * Gets the permission.
     *
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Checks if is inheritable.
     *
     * @return true, if is inheritable
     */
    public boolean isInheritable() {
        return inheritable;
    }

    /**
     * Gets the rank.
     *
     * @return the rank
     */
    public int getRank() {
        return rank;
    }
}
