package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class ActivityPointNotificationComposer extends MessageComposer {

    private final int points;
    private final int additionalPoints;

    public ActivityPointNotificationComposer(int points, int additionalPoints) {
        this.points = points;
        this.additionalPoints = additionalPoints;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.points);
        response.writeInt(this.additionalPoints);
        response.writeInt(0); // Currency type
    }

    @Override
    public short getHeader() {
        return Outgoing.ActivityPointNotificationComposer;
    }
}
