package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class AvatarAspectUpdateMessageComposer extends MessageComposer {

    private String figure;
    private String gender;

    public AvatarAspectUpdateMessageComposer(String figure, String gender) {
        this.figure = figure;
        this.gender = gender;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.AvatarAspectUpdateMessageComposer);
        response.writeString(this.figure);
        response.writeString(this.gender);
    }

    @Override
    public short getHeader() {
        return Outgoing.AvatarAspectUpdateMessageComposer;
    }
}