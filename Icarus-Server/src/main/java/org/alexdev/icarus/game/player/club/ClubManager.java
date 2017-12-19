package org.alexdev.icarus.game.player.club;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.dao.mysql.catalogue.ClubDao;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.util.Util;
import org.alexdev.icarus.util.date.DateUtil;

public class ClubManager {

    /**
     * Handle purchase.
     *
     * @param player the player
     * @param item the bundle item
     * @param amount the amount
     */
    public static void handlePurchase(Player player, CatalogueItem item, int amount) {
        int daysPurchased = 30 * amount;
        String catalogueName = item.getDisplayName();
        
        if (catalogueName.equals("DEAL_HC_2")) {
            daysPurchased = daysPurchased * 3;
        }
        
        if (catalogueName.equals("DEAL_HC_3")) {
            daysPurchased = daysPurchased * 6;
        }
        
        player.send(new PurchaseNotificationMessageComposer(item));
        purchaseDays(player, daysPurchased);
    }
    

    /**
     * Purchase days.
     *
     * @param player the player
     * @param daysPurchased the days purchased
     */
    private static void purchaseDays(Player player, int daysPurchased) {
        long currentTime = DateUtil.getCurrentTimeSeconds();
        long newExpireTime = TimeUnit.DAYS.toSeconds(daysPurchased);
        
        if (player.getSubscription().hasSubscription()) {
            newExpireTime += player.getSubscription().getExpireTime();
            ClubDao.update(player.getEntityId(), newExpireTime, currentTime);
        } else {
            newExpireTime += currentTime;
            ClubDao.create(player.getEntityId(), newExpireTime, currentTime);
        }
   
        player.getSubscription().update(player.getEntityId(), newExpireTime, currentTime);
        player.getSubscription().sendSubscriptionStatus();
    }
}
