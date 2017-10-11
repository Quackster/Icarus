package org.alexdev.icarus.messages.outgoing.user.club;

import org.alexdev.icarus.game.player.club.ClubSubscription;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class SubscriptionMessageComposer extends MessageComposer {

    private ClubSubscription subscription;
    private boolean isMember;

    public SubscriptionMessageComposer(ClubSubscription subscription, boolean isMember) {
        this.subscription = subscription;
        this.isMember = isMember;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.SubscriptionMessageComposer);
        this.response.writeString("habbo_club");

        if (this.isMember) {            
            this.response.writeInt(this.subscription.getDaysLeft());
            this.response.writeInt(2);
            this.response.writeInt(this.subscription.getMonthsLeft());
            this.response.writeInt(this.subscription.getYearsLeft()); 
            
        } else {
            this.response.writeInt(0);
            this.response.writeInt(7);
            this.response.writeInt(0);
            this.response.writeInt(1); 
        }

        this.response.writeBool(true);
        this.response.writeBool(true);
        this.response.writeInt(0);
        this.response.writeInt(0);

        if (this.isMember) {
            this.response.writeInt((int)this.subscription.getDifference());
        } else {
            this.response.writeInt(0);
        }
    }

}
