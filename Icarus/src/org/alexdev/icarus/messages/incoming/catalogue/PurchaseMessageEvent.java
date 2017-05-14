package org.alexdev.icarus.messages.incoming.catalogue;

import java.util.List;

import org.alexdev.icarus.dao.mysql.InventoryDao;
import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.club.ClubManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.common.collect.Lists;

public class PurchaseMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        int pageId = request.readInt();
        int itemId = request.readInt();
        String extraData = request.readString();
        int amount = request.readInt();

        CataloguePage page = CatalogueManager.getPage(pageId);

        if (page.getMinRank() > player.getDetails().getRank()) {
            return;
        }

        CatalogueItem item = page.getItem(itemId);

        this.purchase(player, item, extraData, amount);

        player.getInventory().update();
    }

    private void purchase(Player player, CatalogueItem item, String extraData, int amount) {

        if (amount > 99) {
            amount = 1;
        }

        if (amount > 1 && !item.allowOffer()) {
            //client.send(new AlertMessageComposer(Locale.get("catalog.error.nooffer")));

            return;
        }

        int totalCostCredits;
        // int totalCostPoints;
        // int totalCostActivityPoints;

        if (item.getLimitedSells() >= item.getLimitedTotal() && item.getLimitedTotal() != 0) {
            // client.send(new LimitedEditionSoldOutMessageComposer());
            // TODO: Fix this.
            return;
        }

        if (item.allowOffer()) {
            totalCostCredits = amount > 1 ? ((item.getCostCredits() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostCredits())) : item.getCostCredits();
            //totalCostPoints = amount > 1 ? ((item.getCostOther() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostOther())) : item.getCostOther();
            //totalCostActivityPoints = amount > 1 ? ((item.getCostPixels() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostPixels())) : item.getCostPixels();
        } else {
            totalCostCredits = item.getCostCredits();
            //totalCostPoints = item.getCostOther();
            //totalCostActivityPoints = item.getCostPixels();
        }

        boolean creditsError = false;

        if (player.getDetails().getCredits() < totalCostCredits) {
            player.send(new PurchaseErrorMessageComposer(creditsError, false));
            return;
        }

        if (item.getCostCredits() > 0) {
            player.getDetails().setCredits(player.getDetails().getCredits() - totalCostCredits);
            player.getDetails().sendCredits();
        }

        for (CatalogueBundledItem bundleItem : item.getItems()) {

        	if (bundleItem.getCatalogueItem().getCatalogueName().startsWith("DEAL_HC_")) {
        		ClubManager.handlePurchase(player, bundleItem);
        		return;
        	}
        	
            List<Item> bought = Lists.newArrayList();

            for (int i = 0; i < amount; i++) {
                for (int j = 0; j < item.getAmount(); j++) {
                    
                    Item inventoryItem = InventoryDao.newItem(bundleItem.getItemId(), player.getDetails().getId(), extraData);
                    bought.add(inventoryItem);

                    if (inventoryItem.getDefinition().getInteractionType() == InteractionType.JUKEBOX) {
                        inventoryItem.setExtraData("0");
                    }

                    if (inventoryItem.getDefinition().getInteractionType() == InteractionType.GATE) {
                        inventoryItem.setExtraData("0");
                    }

                    if (inventoryItem.getDefinition().getInteractionType() == InteractionType.TELEPORT) {
                        inventoryItem.setExtraData("0");
                    }

                    player.getInventory().addItem(inventoryItem);
                }
            }

            player.send(new PurchaseNotificationMessageComposer(bundleItem));

        }
    }

}
