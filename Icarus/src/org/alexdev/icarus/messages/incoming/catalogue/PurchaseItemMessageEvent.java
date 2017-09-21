package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.inventory.InventoryNotification;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.club.ClubManager;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class PurchaseItemMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        int pageId = request.readInt();
        int itemId = request.readInt();
        String extraData = request.readString();

        CataloguePage page = CatalogueManager.getPage(pageId);

        if (page.getMinRank() > player.getDetails().getRank()) {
            return;
        }

        CatalogueItem item = page.getItem(itemId);

        if (item.getDisplayName().startsWith("a0 pet")) {

            this.purchasePet(player, item, extraData);
            
            
            
        } else {
            this.purchase(player, item, extraData, request);
        }
        
        player.getInventory().updateItems();
    }

    private void purchasePet(Player player, CatalogueItem item, String extraData) {
        
        boolean creditsError = false;

        if (player.getDetails().getCredits() < item.getCostCredits()) {
            player.send(new PurchaseErrorMessageComposer(creditsError, false));
            return;
        }

        if (item.getCostCredits() > 0) {
            player.getDetails().setCredits(player.getDetails().getCredits() - item.getCostCredits());
            player.getDetails().sendCredits();
        }

        
        String[] petData = extraData.split("\n"); // name (String), race (int) and colour (int)
        String petType = item.getDisplayName().replace("a0 pet", "");

        if (petData.length != 3) {
            return;
        }

        String petName = petData[0];
        int type = Integer.valueOf(petType);
        int race = Integer.valueOf(petData[1]);
        String colour = petData[2];
        
        int petId = PetDao.createPet(player.getEntityId(), petData[0], type, race, colour);
        Pet pet = new Pet(petId, petName, Pet.DEFAULT_LEVEL, Pet.DEFAULT_HAPPINESS, Pet.DEFAULT_EXPERIENCE, Pet.DEFAULT_ENERGY, player.getEntityId(), colour, race, type, false, -1, 0, false, (int)Util.getCurrentTimeSeconds(), 0, 0, 0);
        
        player.getInventory().addPet(pet, InventoryNotification.ALERT);
        player.getInventory().updatePets();
        
        player.send(new PurchaseNotificationMessageComposer());
    }

    private void purchase(Player player, CatalogueItem item, String extraData, ClientMessage request) {

        int amount = request.readInt();

        if (amount > 100) {
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

            if (bundleItem.getCatalogueItem().getDisplayName().startsWith("DEAL_HC_")) {
                ClubManager.handlePurchase(player, bundleItem, amount);
                return;
            }

            for (int i = 0; i < amount; i++) {
                bundleItem.getItemDefinition().handleDefinitionPurchase(player, extraData);
            }

            player.send(new PurchaseNotificationMessageComposer(bundleItem));
        }
    }
}
