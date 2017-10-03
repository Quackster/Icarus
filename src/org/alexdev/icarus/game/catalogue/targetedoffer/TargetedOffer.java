package org.alexdev.icarus.game.catalogue.targetedoffer;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.mysql.catalogue.TargetedOfferDao;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

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
    
    public TargetedOffer(int id, String title, String description, int costCredits, int costActivityPoints, int activityPointsType, int purchaseLimit, String largeImage, String smallImage, long expiryDate, String itemIds) {
        
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
        this.items = new ArrayList<>();
        this.blacklist = TargetedOfferDao.getOfferBlacklist(this.id);
        
        try {
            
            for (String itemId : itemIds.split(";")) {
                this.items.add(Integer.valueOf(itemId));
            }
            
        } catch (Exception e) {
            Log.exception(e);
        }
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the cost credits.
     *
     * @return the cost credits
     */
    public int getCostCredits() {
        return costCredits;
    }

    /**
     * Gets the cost activity points.
     *
     * @return the cost activity points
     */
    public int getCostActivityPoints() {
        return costActivityPoints;
    }

    /**
     * Gets the activity points type.
     *
     * @return the activity points type
     */
    public int getActivityPointsType() {
        return activityPointsType;
    }

    /**
     * Gets the purchase limit.
     *
     * @return the purchase limit
     */
    public int getPurchaseLimit() {
        return purchaseLimit;
    }

    /**
     * Gets the large image.
     *
     * @return the large image
     */
    public String getLargeImage() {
        return largeImage;
    }

    /**
     * Gets the small image.
     *
     * @return the small image
     */
    public String getSmallImage() {
        return smallImage;
    }

    /**
     * Gets the expiry date.
     *
     * @return the expiry date
     */
    public long getExpiryDate() {
        return expiryDate;
    }
    
    /**
     * Checks if is expired.
     *
     * @return true, if is expired
     */
    public boolean isExpired() {
        return !(this.expiryDate > Util.getCurrentTimeSeconds());
    }

    /**
     * Gets the items.
     *
     * @return the items
     */
    public List<Integer> getItems() {
        return items;
    }

    /**
     * Checks if is user blacklisted.
     *
     * @param userId the user id
     * @return true, if is user blacklisted
     */
    public boolean isUserBlacklisted(int userId) {
        return blacklist.contains(userId);
    }

    /**
     * Adds the user to blacklist.
     *
     * @param userId the user id
     */
    public void addUserToBlacklist(int userId) {
        this.blacklist.add(userId);
        TargetedOfferDao.addUserToBlacklist(this.id, userId);
    }  
}
