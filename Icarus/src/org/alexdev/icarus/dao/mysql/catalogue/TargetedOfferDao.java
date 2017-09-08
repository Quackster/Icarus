package org.alexdev.icarus.dao.mysql.catalogue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.Storage;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

public class TargetedOfferDao {

    public static TargetedOffer getOffer() {
        
        TargetedOffer offer = null;
        
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Dao.getStorage().getConnection();

            preparedStatement = Dao.getStorage().prepare("SELECT * FROM targeted_offers WHERE expire_time > ? AND enabled = 1", sqlConnection);
            preparedStatement.setLong(1, Util.getCurrentTimeSeconds());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                offer = new TargetedOffer(resultSet.getInt("id"), resultSet.getString("title"), resultSet.getString("description"), resultSet.getInt("credits"), resultSet.getInt("activity_points"), resultSet.getInt("activity_points_type"), resultSet.getInt("purchase_limit"), resultSet.getString("large_image"), resultSet.getString("small_image"), resultSet.getInt("expire_time"));

            }

        } catch (Exception e) {
            Log.exception(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }
        
        return offer;
    }
}
