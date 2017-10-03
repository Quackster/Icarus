package org.alexdev.icarus.dao.mysql.catalogue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

public class TargetedOfferDao {

    /**
     * Gets the offers.
     *
     * @return the offers
     */
    public static Map<Integer, TargetedOffer> getOffers() {
        
        Map<Integer, TargetedOffer> offers = new HashMap<>();
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("SELECT * FROM targeted_offers WHERE expire_time > ? AND enabled = 1", sqlConnection);
            preparedStatement.setLong(1, Util.getCurrentTimeSeconds());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                offers.put(resultSet.getInt("id"), new TargetedOffer(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("description"), resultSet.getInt("credits"), resultSet.getInt("activity_points"), resultSet.getInt("activity_points_type"), resultSet.getInt("purchase_limit"), resultSet.getString("large_image"), resultSet.getString("small_image"), resultSet.getLong("expire_time"), resultSet.getString("items")));

            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return offers;
    }
    
    /**
     * Gets the offer blacklist.
     *
     * @param id the id
     * @return the offer blacklist
     */
    public static List<Integer> getOfferBlacklist(int id) {
        
        List<Integer> blacklist = new ArrayList<>();
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("SELECT user_id FROM targeted_offers_blacklist WHERE offer_id = ?", sqlConnection);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                blacklist.add(resultSet.getInt("user_id"));
            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return blacklist;
    }
    
    /**
     * Adds the user to blacklist.
     *
     * @param offerId the offer id
     * @param userId the user id
     */
    public static void addUserToBlacklist(int offerId, int userId) {
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("INSERT INTO targeted_offers_blacklist (offer_id, user_id) VALUES (?, ?)", sqlConnection);
            preparedStatement.setInt(1, offerId);
            preparedStatement.setInt(2, userId);
            preparedStatement.execute();

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
    }
}
