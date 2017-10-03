package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Util;

public class RoomNotificationComposer extends MessageComposer {

    private String title;
    private String message;
    private String image;
    private String hotelName;
    private String url;
    
    public RoomNotificationComposer(String title, String message, String image, String hotelName, String url) {
        this.title = title;
        this.message = message;
        this.image = image;
        this.hotelName = hotelName;
        this.url = url;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomNotificationComposer);
        this.response.writeString(this.image);
        this.response.writeInt(Util.isNullOrEmpty(this.hotelName) ? 2 : 4);
        this.response.writeString("title");
        this.response.writeString(this.title);
        this.response.writeString("message");
        this.response.writeString(this.message);

        if (!Util.isNullOrEmpty(this.hotelName)) {
            this.response.writeString("linkUrl");
            this.response.writeString(this.url);
            this.response.writeString("linkTitle");
            this.response.writeString(this.hotelName);
        }
        
    }
}
