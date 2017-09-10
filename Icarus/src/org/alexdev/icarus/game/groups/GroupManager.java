package org.alexdev.icarus.game.groups;

import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.groups.GroupItemDao;
import org.alexdev.icarus.game.groups.types.GroupBackgroundColour;
import org.alexdev.icarus.game.groups.types.GroupBase;
import org.alexdev.icarus.game.groups.types.GroupBaseColour;
import org.alexdev.icarus.game.groups.types.GroupSymbol;
import org.alexdev.icarus.game.groups.types.GroupSymbolColour;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GroupManager {

    private static List<GroupBase> bases;
    private static List<GroupSymbol> symbols;
    private static List<GroupBaseColour> baseColours;
    private static Map<Integer, GroupSymbolColour> symbolColours;
    private static Map<Integer, GroupBackgroundColour> backgroundColours;

    public static void load() {

        if (bases != null) {
            bases.clear();
            symbols.clear();
            baseColours.clear();
            symbolColours.clear();
            backgroundColours.clear();
        } else {
            bases = Lists.newArrayList();
            symbols = Lists.newArrayList();
            baseColours = Lists.newArrayList();
            symbolColours = Maps.newHashMap();
            backgroundColours = Maps.newHashMap();
        }

        GroupItemDao.getGroupItems(bases, symbols, baseColours, symbolColours, backgroundColours);
    }
    
    private static String format(int num) {
        return (num < 10 ? "0" : "") + num;
    }

    public static String generateBadge(int guildBase, int guildBaseColor, List<Integer> guildStates) {
        String badgeImage = "b" + format(guildBase) + "" + format(guildBaseColor);

        for (int i = 0; i < 3 * 4; i += 3) {
            badgeImage += i >= guildStates.size() ? "s" : "s" + format(guildStates.get(i)) + format(guildStates.get(i + 1)) + "" + guildStates.get(i + 2);
        }

        return badgeImage;
    }

    public static List<GroupBase> getBases() {
        return bases;
    }

    public static List<GroupSymbol> getSymbols() {
        return symbols;
    }

    public static List<GroupBaseColour> getBaseColours() {
        return baseColours;
    }

    public static Map<Integer, GroupSymbolColour> getSymbolColours() {
        return symbolColours;
    }

    public static Map<Integer, GroupBackgroundColour> getBackgroundColours() {
        return backgroundColours;
    }
}
