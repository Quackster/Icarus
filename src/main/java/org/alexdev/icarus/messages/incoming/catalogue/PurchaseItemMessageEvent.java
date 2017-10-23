package org.alexdev.icarus.messages.incoming.catalogue;

import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.game.inventory.InventoryNotification;
import org.alexdev.icarus.game.inventory.effects.Effect;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.club.ClubManager;
import org.alexdev.icarus.game.room.RoomManager;
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
        int amount = request.readInt();

        CataloguePage page = CatalogueManager.getInstance().getPage(pageId);

        if (page.getMinRank() > player.getDetails().getRank()) {
            return;
        }

        CatalogueItem item = page.getItem(itemId);

        if (item.getItemDefinition().getType() == ItemType.PET) {
            this.purchasePet(player, item, extraData);
            return;
        };

        if (item.getItemDefinition().getType() == ItemType.EFFECT) {
            this.purchaseEffect(player, item);
            return;
        }

        this.purchase(player, item, extraData, amount);

    }

    /**
     * Purchase handler for purchasing pets
     *
     * @param player the player
     * @param item the pet purchased
     * @param extraData the extra data to read to know the name, race of the pet.
     */
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

    /**
     * Purchase handler for purchasing effects.
     *
     * @param player the player
     * @param item the item purchased
     */
    private void purchaseEffect(Player player, CatalogueItem item) {

        /*3859}
--------------------
Outgoing(2386, 16, _-04Q) -> [0][0][0][9]R[0][11]h’[0][0]IÊ[0][0][0][0][0][1]
{l}{u:2386}{i:747666}{i:18890}{s:}{i:1}
--------------------
Incoming(277, 69, _-14, _-2mM) <- [0][0][0]E[1][0][0]IÊ[0]avatar_effect101[0][0][0][0][0][0][0][0]–[0][0][0][0][0][0][0][0][1][0][1]e[0][0][0]e[0][0][0][0][0][1][0][0][0][0][0][0][0][0][0][0][0][0][0][0]
--------------------
Incoming(3182, 14, _-6dE, _-30-) <- [0][0][0][12]n[0][0][4]~ÿÿÿj[0][0][0][0]
--------------------
Incoming(818, 15, _-03E, _-5n4) <- [0][0][0][3]2[0][0][0]e[0][0][0][0][0][9]:€[0]
--------------------
Outgoing(1483, 6, _-2Nu) -> [0][0][0][6][5]Ë[0][0][0][3]
{l}{u:1483}{i:3}*/


        int effectId = item.getItemDefinition().getSpriteId();

        Effect effect = player.getInventory().getEffectManager().addEffect(effectId);

        if (effect != null) {
            player.send(player.getInventory().getEffectManager().createEffectComposer(effect));
        }
    }

    /**
     * Purchase handler for purchasing normal items.
     *
     * @param player the player
     * @param item the item purchased
     * @param extraData any extra data to apply to the new item
     * @param amount the amount purchased
     */
    private void purchase(Player player, CatalogueItem item, String extraData, int amount) {

        if (amount > 100) {
            amount = 1;
        }

        if (amount > 1 && !item.allowOffer()) {
            //client.send(new AlertMessageComposer(Locale.get("catalog.error.nooffer")));
            return;
        }

        int totalCostCredits;
        // int totalCostPoints;
        int totalCostActivityPoints;

        if (item.getLimitedSells() >= item.getLimitedTotal() && item.getLimitedTotal() != 0) {
            // client.send(new LimitedEditionSoldOutMessageComposer());
            // TODO: Fix this.
            return;
        }

        if (item.allowOffer()) {
            totalCostCredits = amount > 1 ? ((item.getCostCredits() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostCredits())) : item.getCostCredits();
            totalCostActivityPoints = amount > 1 ? ((item.getCostPixels() * amount) - ((int) Math.floor((double) amount / 6) * item.getCostPixels())) : item.getCostPixels();
        } else {
            totalCostCredits = item.getCostCredits();
            totalCostActivityPoints = item.getCostPixels();
        }

        boolean creditsError = false;

        if (player.getDetails().getCredits() < totalCostCredits) {
            player.send(new PurchaseErrorMessageComposer(creditsError, false));
            return;
        }

        if (totalCostCredits > 0) {
            player.getDetails().setCredits(player.getDetails().getCredits() - totalCostCredits);
            player.getDetails().sendCredits();
        }

        if (totalCostActivityPoints > 0) {
            player.getDetails().setDuckets(player.getDetails().getDuckets() - totalCostActivityPoints);
            player.getDetails().sendDuckets();
        }

        this.handlePurchase(item, player, amount, extraData);
    }

    private void handlePurchase(final CatalogueItem item, final Player player, final int amount, final String extraData) {

        RoomManager.getInstance().getScheduledPool().execute(() -> {
            for (int listingAmount = 0; listingAmount < item.getAmount(); listingAmount++) {
                if (item.getDisplayName().startsWith("DEAL_HC_")) {
                    ClubManager.handlePurchase(player, item, amount);
                    return;
                }

                for (int i = 0; i < amount; i++) {
                    item.getItemDefinition().handlePurchase(player, extraData);
                }

                player.send(new PurchaseNotificationMessageComposer(item));
                player.getInventory().updateItems();
            }
        });
    }
}
