package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class TargettedOfferDataComposer extends MessageComposer {

    private boolean minimised;

    @Override
    public void write() {
        this.response.init(Outgoing.TargettedOfferDataComposer);
        this.response.writeInt(this.minimised ? 4 : 1); 
        this.response.writeInt(190); // layout
        this.response.writeString("000000000000");  // must be 12 characters long
        this.response.writeString("000000000");     // must be 9 characters long
        this.response.writeInt(105); // credits 
        this.response.writeInt(105); // how many activity points
        this.response.writeInt(0); // activity points type (like diamonds = 0)
        this.response.writeInt(2); // purchase limit
        this.response.writeInt(259199); // time in seconds until expiry 
        this.response.writeString("targeted.offer.bf16_tko_gr1.title"); // title - can either be external text entry or just straight text
        this.response.writeString("targeted.offer.bf16_tko_gr1.desc"); // description - can either be external text entry or just straight text
        this.response.writeString("targetedoffers/tko_xmas16.png"); // image large
        this.response.writeString("targetedoffers/tto_blkfri_20_small.png"); // image small
        this.response.writeInt(1); 
        this.response.writeInt(15); 
        this.response.writeString("HC_1_MONTH_INTERNAL");
        this.response.writeString("xmas13_snack");
        this.response.writeString("deal_10bronzecoins");
        this.response.writeString("xmas_c15_roof1");
        this.response.writeString("xmas12_snack");
        this.response.writeString("deal_10bronzecoins");
        this.response.writeString("deal_10bronzecoins");
        this.response.writeString("xmas_c15_buildmid1");
        this.response.writeString("xmas_c15_buildbase1");
        this.response.writeString("deal_10bronzecoins");
        this.response.writeString("clothing_longscarf"); 
        this.response.writeString("deal_10bronzecoins");
        this.response.writeString("deal_10bronzecoins");
        this.response.writeString("BADGE");
        this.response.writeString("deal_10bronzecoins");
    }
}
