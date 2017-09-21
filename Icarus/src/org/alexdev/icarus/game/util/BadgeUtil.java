package org.alexdev.icarus.game.util;

import java.util.List;

public class BadgeUtil {
    
    /**
     * Format group state.
     *
     * @param num the number
     * @return the string
     */
    private static String format(int num) {
        return (num < 10 ? "0" : "") + num;
    }

    /**
     * Generate the group badge.
     *
     * @param guildBase the guild base
     * @param guildBaseColor the guild base color
     * @param guildStates the guild states
     * @return the string
     */
    public static String generate(int guildBase, int guildBaseColor, List<Integer> guildStates) {
        String badgeImage = "b" + format(guildBase) + "" + format(guildBaseColor);

        for (int i = 0; i < 3 * 4; i += 3) {
            badgeImage += i >= guildStates.size() ? "s" : "s" + format(guildStates.get(i)) + format(guildStates.get(i + 1)) + "" + guildStates.get(i + 2);
        }

        return badgeImage;
    }
}

