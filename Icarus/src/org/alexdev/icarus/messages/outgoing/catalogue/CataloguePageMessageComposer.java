package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.catalogue.CataloguePage;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class CataloguePageMessageComposer extends MessageComposer {

    private CataloguePage page;
    private String type;

    public CataloguePageMessageComposer(CataloguePage page, String type) {
        this.page = page;
        this.type = type;
    }

    @Override
    public void write() {

        this.response.init(Outgoing.CataloguePageMessageComposer);
        this.response.writeInt(this.page.getId());
        this.response.writeString(this.type);
        this.response.writeString(page.getLayout());
        
        this.response.writeInt(page.getImages().size());
        for (String image : page.getImages()) {
            this.response.writeString(image);
        }
        
       this.response.writeInt(page.getTexts().size());
        for (String text : page.getTexts()) {
            this.response.writeString(text);
        }

        this.response.writeInt(page.getItems().size()); 

        for (CatalogueItem item : page.getItems()) {
            item.serialise(response);
        }

        this.response.writeInt(-1);
        this.response.writeBool(false);
        
        if (page.getLayout().equals("frontpage4")) {

            this.response.writeInt(4);

            this.response.writeInt(1);
            this.response.writeString("New Fitness Duck Bundle!");
            this.response.writeString("catalogue/feature_cata_vert_mallbundle3.png");
            this.response.writeInt(0);
            this.response.writeString("lympix16shop");
            this.response.writeInt(-1);

            this.response.writeInt(2);
            this.response.writeString("Clothes Shop");
            this.response.writeString("catalogue/feature_cata_hort_clothes.png");
            this.response.writeInt(0);
            this.response.writeString("clothing_top_picks");
            this.response.writeInt(-1);

            this.response.writeInt(3);
            this.response.writeString("The Icarus Pet Shop");
            this.response.writeString("catalogue/feature_cata_hort_pets.png");
            this.response.writeInt(0);
            this.response.writeString("pets_info");
            this.response.writeInt(-1);

            this.response.writeInt(4);
            this.response.writeString("Become a HC Member");
            this.response.writeString("catalogue/feature_cata_hort_HC_b.png");
            this.response.writeInt(0);
            this.response.writeString("hc_membership");
            this.response.writeInt(-1);

        }
        else {
            this.response.writeInt(0);
        }
    }
}
