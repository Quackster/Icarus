package org.alexdev.icarus.game.player.club;

import org.alexdev.icarus.dao.mysql.ClubDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.user.SubscriptionMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.UserRightsComposer;
import org.alexdev.icarus.util.Util;

public class ClubSubscription {

    private long expireTime;
    private long boughtTime;
    private long difference;
    private int userId;
    private Player player;

    public ClubSubscription(Player player) {
        this.userId = -1;
        this.player = player;
    }

    public void update(int userId, long expireTime, long boughtTime) {
        this.userId = userId;
        this.expireTime = expireTime;
        this.boughtTime = boughtTime;

        if (this.expireTime > 0) {
            if (!this.hasSubscription()) {
                ClubDao.delete(this.userId);
                this.expireTime = 0;
                this.boughtTime = 0;
                return;
            }
        }
       
        this.difference = this.expireTime - Util.getCurrentTimeSeconds();
    }

    public boolean hasSubscription() {
        
        if (this.userId == -1) {
            return false;
        }
        
        if (Util.getCurrentTimeSeconds() >= this.expireTime) {
            return false;
        }
        
        if (this.boughtTime == 0) {
            return false;
        }
        
        return true;
    }

    public int getDaysLeft() {

        if (this.hasSubscription()) {
            int result = (int) (this.difference  % (86400 * 30)) / 86400;

            if (result < 0) {
                result = 0; 
            }

            return result;
        }

        return 0;
    }

    public int getMonthsLeft() {

        if (this.hasSubscription()) {
            int result = (int) (this.difference / (60 * 60 * 24 * 30));

            if (result < 0) {
                result = 0;
            }

            return result;
        }

        return 0;
    }

    public int getYearsLeft() {

        if (this.hasSubscription()) {
            int result = (int) (this.difference / (60 * 60 * 24 * 365));

            if (result < 0) {
                result = 0;
            }

            return result;
        }

        return 0;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getBoughtTime() {
        return boughtTime;
    }

    public void setBoughtTime(long boughtTime) {
        this.boughtTime = boughtTime;
    }

    public long getDifference() {
        return this.difference;
    }

    public void sendSubscriptionStatus() {
        
        this.player.send(new SubscriptionMessageComposer(this.player));
        this.player.send(new UserRightsComposer(this.player.getSubscription().hasSubscription(), this.player.getDetails().getRank()));
    }

}
