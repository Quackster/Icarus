package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;
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
    public void compose(Response response) {
        response.writeString(this.image);
        response.writeInt(Util.isNullOrEmpty(this.hotelName) ? 2 : 4);
        response.writeString("title");
        response.writeString(this.title);
        response.writeString("message");
        response.writeString(this.message);

        if (!Util.isNullOrEmpty(this.hotelName)) {
            response.writeString("linkUrl");
            response.writeString(this.url);
            response.writeString("linkTitle");
            response.writeString(this.hotelName);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomNotificationComposer;
    }
}