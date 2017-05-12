package org.alexdev.icarus.game.item.serialise;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemSerialise;
import org.alexdev.icarus.server.api.messages.Response;

public class FloorItemSerialise implements ItemSerialise {

    private Item item;
    
    public FloorItemSerialise(Item item) {
        this.item = item;
    }

    @Override
    public void serialiseInventory(Response response) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void serialiseItem(Response response) {
        
        response.writeInt(this.item.getId());
        response.writeInt(this.item.getDefinition().getSpriteId());
        response.writeInt(this.item.getPosition().getX());
        response.writeInt(this.item.getPosition().getY());
        response.writeInt(this.item.getPosition().getRotation());
        response.writeString("" + this.item.getPosition().getZ());
        response.writeString("" + this.item.getPosition().getZ());

        if (this.item.getDefinition().getInteractionType() == InteractionType.YOUTUBETV) {
            response.writeInt(0);
            response.writeInt(1);
            response.writeInt(1);
            response.writeString("THUMBNAIL_URL");
            response.writeString("/deliver/" + "");
            
        } else if (this.item.getDefinition().getInteractionType() == InteractionType.BADGE_DISPLAY) {
            response.writeInt(0);
            response.writeInt(2);
            response.writeInt(4);

            if (this.item.getExtraData().length() > 0) {

                response.writeString("0");
                
                for (int i = 0; i <= this.item.getExtraData().split(Character.toString((char)9)).length - 1; i++) {
                    response.writeString(this.item.getExtraData().split(Character.toString((char)9))[i]);
                }
                
            } else {
                response.writeInt(0);
            }

        } else if (this.item.getDefinition().getInteractionType() == InteractionType.BG_COLORBACKGROUND) {
            response.writeInt(1); // is ads
            response.writeInt(5); //type
            response.writeInt(4);
            response.writeInt(0); // online?
            response.writeInt(0);
            response.writeInt(0);
            response.writeInt(0);
        } else if (this.item.getDefinition().getInteractionType() == InteractionType.MANNEQUIN) {

            String[] Extradatas = this.item.getExtraData().split(";");

            if (this.item.getExtraData().contains(";") && Extradatas.length >= 3)  {
                response.writeInt(1);
                response.writeInt(1);
                response.writeInt(3);
                response.writeString("GENDER");
                response.writeString(Extradatas[0]);
                response.writeString("FIGURE");
                response.writeString(Extradatas[1]);
                response.writeString("OUTFIT_NAME");
                response.writeString(Extradatas[2]);
            } else {
                response.writeInt(1);
                response.writeInt(1);
                response.writeInt(3);
                response.writeString("GENDER");
                response.writeString("m");
                response.writeString("FIGURE");
                response.writeString("");
                response.writeString("OUTFIT_NAME");
                response.writeString("");
            }
        } else {
            response.writeInt((this.item.getDefinition().getInteractionType() == InteractionType.DEFAULT) ? 0 : 1);
            response.writeInt(0);
        }
        
        if (this.item.getDefinition().getInteractionType() == InteractionType.TROPHY) {
            response.writeString("  " + this.item.getExtraData());  
        } else {
            response.writeString(this.item.getExtraData());  
        }
        
        response.writeInt(-1); // secondsToExpiration
        response.writeInt(this.item.getDefinition().getInteractionType() != InteractionType.DEFAULT ? 1 : 0);
        response.writeInt(this.item.getUserId());
    }
}
