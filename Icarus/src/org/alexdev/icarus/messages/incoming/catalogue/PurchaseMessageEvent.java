package org.alexdev.icarus.messages.incoming.catalogue;

import java.util.List;

import org.alexdev.icarus.dao.mysql.InventoryDao;
import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.furniture.FurnitureManager;
import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
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
        int priceAmount = request.readInt();

        CataloguePage page = CatalogueManager.getPage(pageId);

        if (page.getMinRank() > player.getDetails().getRank()) {
            return;
        }

        CatalogueItem item = page.getItem(itemId);

        this.purchase(player, item, extraData, priceAmount);

        player.getInventory().update();
    }

    private void purchase(Player player, CatalogueItem item, String extraData, int priceAmount) {

        int finalAmount = priceAmount;

        if (priceAmount > 5) {

            int discount = ((int) Math.floorDiv(priceAmount, 6) * 6);

            int freeItems = (discount - 3) / 3;

            if (priceAmount >= 42) {
                freeItems++; // add another free item if more than 42 items 8)
            }

            if (priceAmount >= 99) { // not divisible by 3
                freeItems = 33;
            }

            finalAmount = priceAmount - freeItems;
        }

        int amountPurchased = item.getAmount();



        boolean creditsError = false;

        if (player.getDetails().getCredits() < (item.getCostCredits() * finalAmount)) {
            player.send(new PurchaseErrorMessageComposer(creditsError, false));
            return;
        }

        if (item.getCostCredits() > 0) {
            player.getDetails().setCredits(player.getDetails().getCredits() - item.getCostCredits());
            player.getDetails().sendCredits();
        }

        for (CatalogueBundledItem bundleItem : item.getItems()) {

            ItemDefinition definition = FurnitureManager.getFurnitureById(bundleItem.getItemId());

            if (definition.getInteractionType() == InteractionType.TELEPORT) {
                amountPurchased = 2;
            }
            
            List<Item> bought = Lists.newArrayList();

            for (int i = 0; i < amountPurchased; i++) {
                player.send(new PurchaseNotificationMessageComposer(bundleItem, finalAmount));

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
    }

}
