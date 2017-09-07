package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class HabboClubCenterComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.HabboClubCenterComposer);
        this.response.writeInt(7 * 365); // streak duration in days
        this.response.writeString("01-01-2017"); // join date
        
        this.response.writeInt(1); 
        this.response.writeInt(2);
        this.response.writeInt(3);
        this.response.writeInt(4); 
        this.response.writeInt(5); 
        
        this.response.writeInt(200); // coins spent
        this.response.writeInt(10); // % percentage of habbo creditz
        this.response.writeInt(100000); // payday countdown in minutes
    }

}
