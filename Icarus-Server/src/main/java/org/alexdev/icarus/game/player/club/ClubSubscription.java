package org.alexdev.icarus.game.player.club;

import org.alexdev.icarus.dao.mysql.catalogue.ClubDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.user.club.SubscriptionMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.settings.UserRightsMessageComposer;
import org.alexdev.icarus.util.Util;
import org.alexdev.icarus.util.date.DateUtil;

public class ClubSubscription {
    
    private Player player;
    private long expireTime;
    private long boughtTime;
    private long difference;
    private int userId;

    public ClubSubscription(Player player) {
        this.userId = -1;
        this.player = player;
    }

    /**
     * Update.
     *
     * @param userId the user id
     * @param expireTime the expire time
     * @param boughtTime the bought time
     */
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
       
        this.difference = this.expireTime - DateUtil.getCurrentTimeSeconds();
    }

    /**
     * Checks for subscription.
     *
     * @return true, if successful
     */
    public boolean hasSubscription() {
        
        if (this.userId == -1) {
            return false;
        }
        
        if (DateUtil.getCurrentTimeSeconds() >= this.expireTime) {
            return false;
        }
        
        if (this.boughtTime == 0) {
            return false;
        }
        
        return true;
    }

    /**
     * Send subscription status.
     */
    public void sendSubscriptionStatus() {
        boolean isMember = this.player.getSubscription().hasSubscription();

        this.player.sendQueued(new SubscriptionMessageComposer(this, isMember));
        this.player.sendQueued(new UserRightsMessageComposer(isMember, this.player.getDetails().getRank()));
        this.player.flushQueue();
    }

    /**
     * Gets the days left.
     *
     * @return the days left
     */
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

    /**
     * Gets the months left.
     *
     * @return the months left
     */
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

    /**
     * Gets the years left.
     *
     * @return the years left
     */
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

    /**
     * Gets the expire time.
     *
     * @return the expire time
     */
    public long getExpireTime() {
        return expireTime;
    }

    /**
     * Sets the expire time.
     *
     * @param expireTime the new expire time
     */
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * Gets the bought time.
     *
     * @return the bought time
     */
    public long getBoughtTime() {
        return boughtTime;
    }

    /**
     * Sets the bought time.
     *
     * @param boughtTime the new bought time
     */
    public void setBoughtTime(long boughtTime) {
        this.boughtTime = boughtTime;
    }

    /**
     * Gets the difference.
     *
     * @return the difference
     */
    public long getDifference() {
        return this.difference;
    }
    
    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return this.userId;
    }
}
