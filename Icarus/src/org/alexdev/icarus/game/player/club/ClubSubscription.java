package org.alexdev.icarus.game.player.club;

public class ClubSubscription {

    private long expireTime;
    private long boughtTime;
   
    public ClubSubscription(long expireTime, long boughtTime) {
        this.expireTime = expireTime;
        this.boughtTime = boughtTime;
    }

    public boolean isExpired() {
        
        if (this.expireTime > 0) {
            
            /*
             * If our current date is bigger then the expire date 
             * then the subscription has expired
             */
            return System.currentTimeMillis() > this.expireTime;
        } else {
            return false; // Infinite subscription =)
        }
    }
    
    public int getDaysLeft() {

        if (this.expireTime > 0) {
            
            int result = (int) (Math.abs(this.expireTime - System.currentTimeMillis()) / 60 / 60 / 24);
            
            if (result < 0) {
                result = 0;
            }
            
            return result;
        }
        
        return 0;
    }

    public int getMonthsLeft() {
        
        if (this.expireTime > 0) {
            
            int result = (int) (Math.abs(this.expireTime - System.currentTimeMillis()) / 60 / 60 / 24 / 30);
            
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
   
}
