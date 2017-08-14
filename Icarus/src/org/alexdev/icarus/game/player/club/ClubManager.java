package org.alexdev.icarus.game.player.club;

import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.dao.mysql.ClubDao;
import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.SubscriptionMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.UserRightsComposer;
import org.alexdev.icarus.util.Util;

public class ClubManager {

    public static void handlePurchase(Player player, CatalogueBundledItem bundleItem) {
        
        int daysPurchased = 30;
        String catalogueName = bundleItem.getCatalogueItem().getCatalogueName();
        
        Log.println(catalogueName);
        
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
            ClubDao.update(player.getDetails().getId(), newExpireTime, currentTime);
        } else {
            newExpireTime += currentTime;
            ClubDao.create(player.getDetails().getId(), newExpireTime, currentTime);
        }
   
        
        player.getSubscription().update(player.getDetails().getId(), newExpireTime, currentTime);
       
     
        Log.println("Difference: " + player.getSubscription().getDifference());
        
        player.send(new SubscriptionMessageComposer(player));
        player.send(new UserRightsComposer(player.getSubscription().hasSubscription(), player.getDetails().getRank()));
        
        Log.println("Days of Habbo Club purchased: " + daysPurchased);
    }

}
