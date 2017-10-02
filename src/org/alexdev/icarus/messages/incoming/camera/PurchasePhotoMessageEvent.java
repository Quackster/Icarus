package org.alexdev.icarus.messages.incoming.camera;

import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.camera.PurchasedPhotoComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchasePhotoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int photoId = 757369158; // top LUL amirite??
        
        ItemDefinition definition = ItemManager.getFurnitureById(photoId);
        
        StringBuilder extraData = new StringBuilder();
        extraData.append("{");
        extraData.append("\"w\":\"" + player.getRoomUser().getMetadata().getString("latestPhotoUrl") + "\",");
        extraData.append("\"t\":\"" + System.currentTimeMillis() + "\",");
        extraData.append("\"u\":\"" + player.getEntityId() + "\",");
        extraData.append("\"n\":\"" + player.getDetails().getName() + "\",");
        extraData.append("\"s\":\"" + player.getEntityId() + "\",");
        extraData.append("\"m\":\"\"");
        extraData.append("}");

        definition.handleDefinitionPurchase(player, extraData.toString());
        
        player.getRoomUser().getMetadata().remove("latestPhotoUrl");
        player.send(new PurchasedPhotoComposer());
    }

}
