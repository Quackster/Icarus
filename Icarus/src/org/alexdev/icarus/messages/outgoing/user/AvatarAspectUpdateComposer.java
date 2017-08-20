package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class AvatarAspectUpdateComposer extends OutgoingMessageComposer {

    private String figure;
    private String gender;

    public AvatarAspectUpdateComposer(String figure, String gender) {
        this.figure = figure;
        this.gender = gender;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.AvatarAspectUpdateMessageComposer);
        this.response.writeString(this.figure);
        this.response.writeString(this.gender);
    }

}