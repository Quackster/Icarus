package org.alexdev.icarus.game.player.club;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.dao.mysql.catalogue.ClubDao;
import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.util.Util;

public class ClubManager {

    public static void handlePurchase(Player player, CatalogueBundledItem bundleItem, int amount) {
        
        int daysPurchased = 30 * amount;
        String catalogueName = bundleItem.getCatalogueItem().getDisplayName();
        
        if (catalogueName.equals("DEAL_HC_2")) {
            daysPurchased = daysPurchased * 3;
        }
        
        if (catalogueName.equals("DEAL_HC_3")) {
            daysPurchased = daysPurchased * 6;
        }
        
        player.send(new PurchaseNotificationMessageComposer(bundleItem));
        
        purchaseDays(player, daysPurchased);
    }
    

    public static void purchaseDays(Player player, int daysPurchased) {

        long currentTime = Util.getCurrentTimeSeconds();
        long newExpireTime = TimeUnit.DAYS.toSeconds(daysPurchased);
        
        if (player.getSubscription().hasSubscription()) {
            newExpireTime += player.getSubscription().getExpireTime();
            ClubDao.update(player.getDetails().getID(), newExpireTime, currentTime);
        } else {
            newExpireTime += currentTime;
            ClubDao.create(player.getDetails().getID(), newExpireTime, currentTime);
        }
   
        player.getSubscription().update(player.getDetails().getID(), newExpireTime, currentTime);
        player.getSubscription().sendSubscriptionStatus();
    }
}
