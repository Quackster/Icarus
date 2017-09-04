package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class CatalogueDiscountComposer extends MessageComposer {

    @Override
    public void write() {
        response.init(Outgoing.CatalogueDiscountComposer);
        response.writeInt(100);//Most you can get.
        response.writeInt(6);
        response.writeInt(1);
        response.writeInt(1);
        response.writeInt(2);//Count
        {
            response.writeInt(40);
            response.writeInt(99);
        }
    }

}
