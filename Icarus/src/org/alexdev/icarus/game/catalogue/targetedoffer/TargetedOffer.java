package org.alexdev.icarus.game.catalogue.targetedoffer;

import java.util.List;

import org.alexdev.icarus.dao.mysql.catalogue.TargetedOfferDao;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

import com.google.common.collect.Lists;

public class TargetedOffer {

    private int id;
    private String title;
    private String description;
    private int costCredits;
    private int costActivityPoints;
    private int activityPointsType;
    private int purchaseLimit;
    private String largeImage;
    private String smallImage;
    private long expiryDate;
    
    private List<Integer> items;
    private List<Integer> blacklist;
    
    public TargetedOffer(int id, String title, String description, int costCredits, int costActivityPoints, int activityPointsType, int purchaseLimit, String largeImage, String smallImage, long expiryDate, String itemIDs) {
        
        this.id = id;
        this.title = title;
        this.description = description;
        this.costCredits = costCredits;
        this.costActivityPoints = costActivityPoints;
        this.activityPointsType = activityPointsType;
        this.purchaseLimit = purchaseLimit;
        this.largeImage = largeImage;
        this.smallImage = smallImage;
        this.expiryDate = expiryDate;
        this.items = Lists.newArrayList();
        this.blacklist = TargetedOfferDao.getOfferBlacklist(this.id);
        
        try {
            
            for (String itemID : itemIDs.split(";")) {
                this.items.add(Integer.valueOf(itemID));
            }
            
        } catch (Exception e) {
            Log.exception(e);
        }
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCostCredits() {
        return costCredits;
    }

    public int getCostActivityPoints() {
        return costActivityPoints;
    }

    public int getActivityPointsType() {
        return activityPointsType;
    }

    public int getPurchaseLimit() {
        return purchaseLimit;
    }

    public String getLargeImage() {
        return largeImage;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public long getExpiryDate() {
        return expiryDate;
    }
    
    public boolean isExpired() {
        return !(this.expiryDate > Util.getCurrentTimeSeconds());
    }

    public List<Integer> getItems() {
        return items;
    }

    public boolean isUserBlacklisted(int userID) {
        return blacklist.contains(userID);
    }

    public void addUserToBlacklist(int userID) {
        this.blacklist.add(userID);
        TargetedOfferDao.addUserToBlacklist(this.id, userID);
    }  
}
