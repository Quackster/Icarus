package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class GiftingSettingsComposer extends MessageComposer {

    @Override
    public void write() {

        this.response.init(Outgoing.GiftingSettingsComposer);
        this.response.writeBool(true);
        this.response.writeInt(1);
        this.response.writeInt(10);
        
        for (int i = 3372; i < 3382;) {
            this.response.writeInt(i);
            i++;
        }
        
        this.response.writeInt(7);
        this.response.writeInt(0);
        this.response.writeInt(1);
        this.response.writeInt(2);
        this.response.writeInt(3);
        this.response.writeInt(4);
        this.response.writeInt(5);
        this.response.writeInt(6);
        this.response.writeInt(11);
        this.response.writeInt(0);
        this.response.writeInt(1);
        this.response.writeInt(2);
        this.response.writeInt(3);
        this.response.writeInt(4);
        this.response.writeInt(5);
        this.response.writeInt(6);
        this.response.writeInt(7);
        this.response.writeInt(8);
        this.response.writeInt(9);
        this.response.writeInt(10);
        this.response.writeInt(7);
        
        for (int i = 187; i < 194;) {
            this.response.writeInt(i);
            i++;
        }
    }
}
