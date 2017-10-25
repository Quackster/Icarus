package org.alexdev.icarus.messages.incoming.camera;

import org.alexdev.icarus.dao.mysql.item.InventoryDao;
import org.alexdev.icarus.game.inventory.InventoryNotification;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.camera.PurchasedPhotoMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchasePhotoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        if (!player.getMetadata().hasMetadata("latestPhotoUrl")) {
            return;
        }

        if (PhotoPricingMessageEvent.COST_CREDITS > 0) {
            if (player.getDetails().getCredits() < PhotoPricingMessageEvent.COST_CREDITS) {
                return;
            }
        }

        if (PhotoPricingMessageEvent.COST_DUCKETS > 0) {
            if (player.getDetails().getDuckets() < PhotoPricingMessageEvent.COST_DUCKETS) {
                return;
            }
        }

        ItemDefinition photoDef = ItemManager.getInstance().getFurnitureByClass("external_image_wallitem_poster_small");

        StringBuilder extraData = new StringBuilder();
        extraData.append("{");
        extraData.append("\"w\":\"" + player.getMetadata().getString("latestPhotoUrl") + "\",");
        extraData.append("\"t\":\"" + System.currentTimeMillis() + "\",");
        extraData.append("\"u\":\"" + player.getEntityId() + "\",");
        extraData.append("\"n\":\"" + player.getDetails().getName() + "\",");
        extraData.append("\"s\":\"" + player.getEntityId() + "\",");
        extraData.append("\"m\":\"\"");
        extraData.append("}");

        Item photo = InventoryDao.newItem(photoDef.getId(), player.getEntityId(), extraData.toString());
        
        player.getInventory().addItem(photo, InventoryNotification.ALERT);
        player.getInventory().updateItems();

        if (PhotoPricingMessageEvent.COST_CREDITS > 0) {
            player.getDetails().setCredits(player.getDetails().getCredits() - PhotoPricingMessageEvent.COST_CREDITS);
            player.getDetails().sendCredits();
        }

        if (PhotoPricingMessageEvent.COST_DUCKETS > 0) {
            player.getDetails().setDuckets(player.getDetails().getDuckets() - PhotoPricingMessageEvent.COST_DUCKETS);
            player.getDetails().sendDuckets();
        }

        player.getDetails().save();

        
        player.getMetadata().remove("latestPhotoUrl");
        player.send(new PurchasedPhotoMessageComposer());
    }

}
