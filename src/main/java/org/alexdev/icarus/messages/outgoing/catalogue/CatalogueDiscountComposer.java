package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class CatalogueDiscountComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
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

    @Override
    public short getHeader() {
        return Outgoing.CatalogueDiscountComposer;
    }
}