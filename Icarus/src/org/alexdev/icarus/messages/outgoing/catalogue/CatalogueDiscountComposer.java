package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class CatalogueDiscountComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.CatalogueDiscountComposer);
        this.response.writeInt(100);//Most you can get.
        this.response.writeInt(6);
        this.response.writeInt(1);
        this.response.writeInt(1);
        this.response.writeInt(2);//Count
        {
            this.response.writeInt(40);
            this.response.writeInt(99);
        }
    }
}
