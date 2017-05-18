package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class SubscriptionMessageComposer extends OutgoingMessageComposer {

    private int daysLeft;
    private int monthsLeft;

    public SubscriptionMessageComposer(int daysLeft, int monthsLeft) {
        this.daysLeft = daysLeft;
        this.monthsLeft = monthsLeft;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.SubscriptionMessageComposer);
        this.response.writeString("habbo_club");
        this.response.writeInt(this.daysLeft);
        this.response.writeInt(2);
        this.response.writeInt(this.monthsLeft);
        this.response.writeInt(1);
        this.response.writeBool(true); // HC
        this.response.writeBool(true); // VIP
        this.response.writeInt(0);
        this.response.writeInt(0);
        this.response.writeInt(495);
    }

}
