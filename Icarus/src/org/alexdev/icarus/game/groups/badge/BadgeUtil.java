package org.alexdev.icarus.game.groups.badge;

import java.util.List;

import org.alexdev.icarus.log.Log;

public class BadgeUtil {
    
    private static String format(int num) {
        return (num < 10 ? "0" : "") + num;
    }

    public static String generate(int guildBase, int guildBaseColor, List<Integer> guildStates) {
        String badgeImage = "b" + format(guildBase) + "" + format(guildBaseColor);

        for (int i = 0; i < 3 * 4; i += 3) {
            badgeImage += i >= guildStates.size() ? "s" : "s" + format(guildStates.get(i)) + format(guildStates.get(i + 1)) + "" + guildStates.get(i + 2);
        }

        return badgeImage;
    }
}

