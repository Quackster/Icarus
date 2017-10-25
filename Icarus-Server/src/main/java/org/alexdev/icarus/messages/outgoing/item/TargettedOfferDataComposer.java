package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.util.Util;

public class TargettedOfferDataComposer extends MessageComposer {

    private TargetedOffer offer;
    private boolean minimised;

    public TargettedOfferDataComposer(TargetedOffer offer) {
        this.offer = offer;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.TargettedOfferDataComposer);
        response.writeInt(this.minimised ? 4 : 1);
        response.writeInt(this.offer.getId());
        response.writeString("000000000000");  // must be 12 characters long
        response.writeString("000000000");     // must be 9 characters long
        response.writeInt(this.offer.getCostCredits()); // credits 
        response.writeInt(this.offer.getCostActivityPoints()); // how many activity points
        response.writeInt(this.offer.getActivityPointsType()); // activity points type (like diamonds = 0)
        response.writeInt(this.offer.getPurchaseLimit()); // purchase limit
        response.writeInt((int) (this.offer.getExpiryDate() - Util.getCurrentTimeSeconds())); // time in seconds until expiry
        response.writeString(this.offer.getTitle()); // title - can either be external text entry or just straight text
        response.writeString(this.offer.getDescription()); // description - can either be external text entry or just straight text
        response.writeString(this.offer.getLargeImage()); // image large
        response.writeString(this.offer.getSmallImage()); // image small
        response.writeInt(1);
        response.writeInt(0); 
        
        /*        //response.init(Outgoing.TargettedOfferDataComposer);
        response.writeInt(this.minimised ? 4 : 1); 
        response.writeInt(330); // Id
        response.writeString("000000000000");  // must be 12 characters long
        response.writeString("000000000");     // must be 9 characters long
        response.writeInt(105); // credits 
        response.writeInt(105); // how many activity points
        response.writeInt(0); // activity points type (like diamonds = 0)
        response.writeInt(2); // purchase limit
        response.writeInt(259199); // time in seconds until expiry 
        response.writeString("targeted.offer.bf16_tko_gr1.title"); // title - can either be external text entry or just straight text
        response.writeString("targeted.offer.bf16_tko_gr1.desc"); // description - can either be external text entry or just straight text
        response.writeString("targetedoffers/tko_xmas16.png"); // image large
        response.writeString("targetedoffers/tto_blkfri_20_small.png"); // image small
        response.writeInt(1);
        response.writeInt(15); 
        response.writeString("HC_1_MONTH_INTERNAL");
        response.writeString("xmas13_snack");
        response.writeString("deal_10bronzecoins");
        response.writeString("xmas_c15_roof1");
        response.writeString("xmas12_snack");
        response.writeString("deal_10bronzecoins");
        response.writeString("deal_10bronzecoins");
        response.writeString("xmas_c15_buildmid1");
        response.writeString("xmas_c15_buildbase1");
        response.writeString("deal_10bronzecoins");
        response.writeString("clothing_longscarf"); 
        response.writeString("deal_10bronzecoins");
        response.writeString("deal_10bronzecoins");
        response.writeString("BADGE");
        response.writeString("deal_10bronzecoins");*/
    }

    @Override
    public short getHeader() {
        return Outgoing.TargettedOfferDataComposer;
    }
}