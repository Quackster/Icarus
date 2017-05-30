package org.alexdev.icarus.game.player.club;

import java.util.Calendar;

import org.alexdev.icarus.util.Util;

public class ClubSubscription {

    private long expireTime;
    private long boughtTime;
    private Calendar calendar;
    private boolean hasClub;
    private long difference;

    public ClubSubscription() {
        this.update(0, 0);
    }
    
    public void update(long expireTime, long boughtTime) {
        
        this.expireTime = expireTime;
        this.boughtTime = boughtTime;
            
        if (this.expireTime > 0) {
            this.hasClub = true;        
            this.difference = this.expireTime - Util.getCurrentTimeSeconds();
        } else {
            this.hasClub = false;
        }
        
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(this.expireTime * 1000); 

    }

    public boolean isExpired() {
        return this.hasClub == false;
    }

    public int getDaysLeft() {

        if (hasClub) {

            int result = (int) (this.difference  % (86400 * 30)) / 86400;

            if (result < 0) {
                result = 0;
            }

            return result;
        }

        return 0;
    }

    public int getMonthsLeft() {

        if (hasClub) {

            int result = (int) (this.difference / (60 * 60 * 24 * 30));

            if (result < 0) {
                result = 0;
            }

            return result;
        }

        return 0;
    }

    public int getYearsLeft() {

        if (hasClub) {

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

    public Calendar getCalendar() {
        return calendar;
    }

    public long getDifference() {
        return this.difference;
    }

}
