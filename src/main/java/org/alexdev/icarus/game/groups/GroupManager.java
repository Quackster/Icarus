package org.alexdev.icarus.game.groups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.dao.mysql.groups.GroupItemDao;
import org.alexdev.icarus.game.groups.types.GroupBackgroundColour;
import org.alexdev.icarus.game.groups.types.GroupBase;
import org.alexdev.icarus.game.groups.types.GroupBaseColour;
import org.alexdev.icarus.game.groups.types.GroupSymbol;
import org.alexdev.icarus.game.groups.types.GroupSymbolColour;

public class GroupManager {

    private static List<GroupBase> bases;
    private static List<GroupSymbol> symbols;
    private static List<GroupBaseColour> baseColours;
    private static Map<Integer, GroupSymbolColour> symbolColours;
    private static Map<Integer, GroupBackgroundColour> backgroundColours;
    private static Map<Integer, Group> groups;

    /**
     * Load all group symbols.
     */
    public static void load() {
        bases = new ArrayList<>();
        symbols = new ArrayList<>();
        baseColours = new ArrayList<>();
        symbolColours = new HashMap<>();
        backgroundColours = new HashMap<>();
        groups = new HashMap<>();

        GroupItemDao.getGroupItems(bases, symbols, baseColours, symbolColours, backgroundColours);
    }
    
    /**
     * Gets the group.
     *
     * @param groupId the group id
     * @return the group
     */
    public static Group getGroup(int groupId) {
        
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
    public static Group loadGroup(int groupId) {
        
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
    public static void unloadGroup(int groupId) {
        
        if (groups.containsKey(groupId)) {
            groups.remove(groupId);
        }
    }

    /**
     * Gets the bases.
     *
     * @return the bases
     */
    public static List<GroupBase> getBases() {
        return bases;
    }

    /**
     * Gets the symbols.
     *
     * @return the symbols
     */
    public static List<GroupSymbol> getSymbols() {
        return symbols;
    }

    /**
     * Gets the base colours.
     *
     * @return the base colours
     */
    public static List<GroupBaseColour> getBaseColours() {
        return baseColours;
    }

    /**
     * Gets the symbol colours.
     *
     * @return the symbol colours
     */
    public static Map<Integer, GroupSymbolColour> getSymbolColours() {
        return symbolColours;
    }

    /**
     * Gets the background colours.
     *
     * @return the background colours
     */
    public static Map<Integer, GroupBackgroundColour> getBackgroundColours() {
        return backgroundColours;
    }
}
