package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.util.Util;

public class SubscriptionMessageComposer extends OutgoingMessageComposer {

    private Player player;

    public SubscriptionMessageComposer(Player player) {
        this.player = player;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.SubscriptionMessageComposer);
        this.response.writeString("habbo_club");

        if (player.getSubscription().isExpired()) {
            this.response.writeInt(0);
            this.response.writeInt(7);
            this.response.writeInt(0);
            this.response.writeInt(1);
        } else {
            this.response.writeInt(player.getSubscription().getDaysLeft());
            this.response.writeInt(2);
            this.response.writeInt(player.getSubscription().getMonthsLeft());
            this.response.writeInt(player.getSubscription().getYearsLeft());            
        }

        this.response.writeBool(true); // HC
        this.response.writeBool(true); // VIP
        this.response.writeInt(0);
        this.response.writeInt(0);

        if (player.getSubscription().isExpired()) {
            this.response.writeInt(0);
        } else {
            this.response.writeInt((int) (player.getSubscription().getDifference()));
        }
    }

}
