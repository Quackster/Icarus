package org.alexdev.icarus.messages.outgoing.user.club;

import org.alexdev.icarus.game.player.club.ClubSubscription;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class SubscriptionMessageComposer extends MessageComposer {

    private ClubSubscription subscription;
    private boolean isMember;

    public SubscriptionMessageComposer(ClubSubscription subscription, boolean isMember) {
        this.subscription = subscription;
        this.isMember = isMember;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.SubscriptionMessageComposer);
        response.writeString("habbo_club");

        if (this.isMember) {
            response.writeInt(this.subscription.getDaysLeft());
            response.writeInt(2);
            response.writeInt(this.subscription.getMonthsLeft());
            response.writeInt(this.subscription.getYearsLeft());

        } else {
            response.writeInt(0);
            response.writeInt(7);
            response.writeInt(0);
            response.writeInt(1);
        }

        response.writeBool(true);
        response.writeBool(true);
        response.writeInt(0);
        response.writeInt(0);

        if (this.isMember) {
            response.writeInt((int) this.subscription.getDifference());
        } else {
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.SubscriptionMessageComposer;
    }
}
