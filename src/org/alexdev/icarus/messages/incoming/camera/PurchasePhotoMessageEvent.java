package org.alexdev.icarus.messages.incoming.camera;

import org.alexdev.icarus.dao.mysql.item.InventoryDao;
import org.alexdev.icarus.game.inventory.InventoryNotification;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.camera.PurchasedPhotoComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchasePhotoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int photoId = 6359;//757369158

        StringBuilder extraData = new StringBuilder();
        extraData.append("{");
        extraData.append("\"w\":\"" + player.getMetadata().getString("latestPhotoUrl") + "\",");
        extraData.append("\"t\":\"" + System.currentTimeMillis() + "\",");
        extraData.append("\"u\":\"" + player.getEntityId() + "\",");
        extraData.append("\"n\":\"" + player.getDetails().getName() + "\",");
        extraData.append("\"s\":\"" + player.getEntityId() + "\",");
        extraData.append("\"m\":\"\"");
        extraData.append("}");

        Item photo = InventoryDao.newItem(photoId, player.getEntityId(), extraData.toString());
        
        player.getInventory().addItem(photo, InventoryNotification.ALERT);
        player.getInventory().updateItems();
        
        player.getDetails().setCredits(-50);
        player.getDetails().sendCredits();
        
        player.getMetadata().remove("latestPhotoUrl");
        player.send(new PurchasedPhotoComposer());
    }

}
