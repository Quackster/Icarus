package org.alexdev.icarus.game.groups;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.dao.mysql.groups.GroupItemDao;
import org.alexdev.icarus.game.groups.types.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupManager {

    private List<GroupBase> bases;
    private List<GroupSymbol> symbols;
    private List<GroupBaseColour> baseColours;
    private Map<Integer, GroupSymbolColour> symbolColours;
    private Map<Integer, GroupBackgroundColour> backgroundColours;
    private Map<Integer, Group> groups;

    private static GroupManager instance;

    public GroupManager() {
        bases = new ArrayList<>();
        symbols = new ArrayList<>();
        baseColours = new ArrayList<>();
        symbolColours = new HashMap<>();
        backgroundColours = new HashMap<>();
        groups = new HashMap<>();

        GroupItemDao.getGroupItems(bases, symbols, baseColours, symbolColours, backgroundColours);
    }

    /**
     * Get group colour code data
     *
     * @param id the colour id
     * @param colourOne whether the group colour is the first or second
     * @return the colour code
     */
    public String getColourCode(int id, boolean colourOne) {
        if (colourOne) {
            if (this.symbolColours.containsKey(id)) {
                return this.symbolColours.get(id).getColour();
            }
        } else {
            if (this.backgroundColours.containsKey(id)) {
                return this.backgroundColours.get(id).getColour();
            }
        }

        return "";
    }

    /**
     * Gets the group.
     *
     * @param groupId the group id
     * @return the group
     */
    public Group getGroup(int groupId) {
        if (groups.containsKey(groupId)) {
            return groups.get(groupId);
        }

        return GroupDao.getGroup(groupId);
    }

    /**
     * Will load the group, and if successful, will
     * add it to the list of stored/active groups.
     *
     * @param groupId
     * @return
     */
    public Group loadGroup(int groupId) {
        Group group = GroupDao.getGroup(groupId);

        if (group != null) {
            groups.put(groupId, group);
        }

        return group;
    }

    /**
     * Unloads the group, called when there's
     * no more users in a room.
     *
     * @param groupId
     */
    public void unloadGroup(int groupId) {
        if (groups.containsKey(groupId)) {
            groups.remove(groupId);
        }
    }

    /**
     * Gets the bases.
     *
     * @return the bases
     */
    public List<GroupBase> getBases() {
        return bases;
    }

    /**
     * Gets the symbols.
     *
     * @return the symbols
     */
    public List<GroupSymbol> getSymbols() {
        return symbols;
    }

    /**
     * Gets the base colours.
     *
     * @return the base colours
     */
    public List<GroupBaseColour> getBaseColours() {
        return baseColours;
    }

    /**
     * Gets the symbol colours.
     *
     * @return the symbol colours
     */
    public Map<Integer, GroupSymbolColour> getSymbolColours() {
        return symbolColours;
    }

    /**
     * Gets the background colours.
     *
     * @return the background colours
     */
    public Map<Integer, GroupBackgroundColour> getBackgroundColours() {
        return backgroundColours;
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static GroupManager getInstance() {
        if (instance == null) {
            instance = new GroupManager();
        }

        return instance;
    }
}